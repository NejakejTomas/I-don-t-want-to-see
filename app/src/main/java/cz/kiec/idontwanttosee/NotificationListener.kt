package cz.kiec.idontwanttosee

import android.app.Notification
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.LocusIdCompat
import androidx.core.graphics.drawable.IconCompat
import cz.kiec.idontwanttosee.NotificationListener.Operation.Companion.toOperation
import cz.kiec.idontwanttosee.repository.RuleRepository
import cz.kiec.idontwanttosee.repository.dbs.entity.Rule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NotificationListener : NotificationListenerService() {
    private val repository: RuleRepository by inject()

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private suspend fun getActions(
        packageName: String,
        isOngoing: Boolean,
        hasProgressBar: Boolean
    ) =
        repository.selectActions(packageName, isOngoing, hasProgressBar)

    private val Notification.hasProgressBar
        get() = progress > 0

    private val Notification.contentTitle
        get() = extras.getString(Notification.EXTRA_TITLE) ?: ""

    private val Notification.contentText
        get() = extras.getString(Notification.EXTRA_TEXT) ?: ""

    private val Notification.application
        get() = extras.getParcelable(
            "android.appInfo",
            ApplicationInfo::class.java
        )?.loadLabel(packageManager) ?: ""

    private val Notification.isOngoing
        get() = flags and Notification.FLAG_ONGOING_EVENT != 0

    private val Notification.autoCancel
        get() = flags and Notification.FLAG_AUTO_CANCEL != 0

    private val Notification.alertOnlyOnce
        get() = flags and Notification.FLAG_ONLY_ALERT_ONCE != 0

    private val Notification.progressMax
        get() = extras.getInt(Notification.EXTRA_PROGRESS_MAX)

    private val Notification.progress
        get() = extras.getInt(Notification.EXTRA_PROGRESS)

    private val Notification.progressIndeterminate
        get() = extras.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE)

    private fun replaceNotification(
        notification: Notification,
        operation: Operation
    ): Notification {
        // TODO: Dynamically assignable
        val channelId = MyApplication.notificationChannels[3].first

        val builder = NotificationCompat.Builder(this@NotificationListener, channelId).apply {
            val smallIcon = notification.smallIcon?.let {
                IconCompat.createFromIcon(
                    this@NotificationListener,
                    it
                )
            }
            val largeIcon = notification.getLargeIcon()

            setCategory(notification.category)
            setContentIntent(notification.contentIntent)
            setAutoCancel((notification.flags and Notification.FLAG_AUTO_CANCEL) != 0)
            setOngoing(notification.isOngoing)
            setAutoCancel(notification.autoCancel)
            setAllowSystemGeneratedContextualActions(false)
            setGroup(notification.group)
            setGroupAlertBehavior(notification.groupAlertBehavior)
            setNumber(notification.number)
            setOnlyAlertOnce(notification.alertOnlyOnce)
            setProgress(
                notification.progressMax,
                notification.progress,
                notification.progressIndeterminate
            )
            setShortcutId(notification.shortcutId)
            notification.locusId?.let { setLocusId(LocusIdCompat.toLocusIdCompat(it)) }
            setSortKey(notification.sortKey)

            setSubText(notification.application)

            smallIcon?.let { setSmallIcon(smallIcon) }

            if (!operation.hideLargeImage) {
                largeIcon?.let { setLargeIcon(largeIcon) }
            }

            if (!operation.hideTitle) {
                setContentTitle(notification.contentTitle)
            }

            if (!operation.hideContent) {
                setContentText(notification.contentText)
            }
        }

        return builder.build()
    }

    private data class Operation(
        val hideTitle: Boolean = false,
        val hideContent: Boolean = false,
        val hideLargeImage: Boolean = false,
    ) {
        infix fun or(that: Operation) = Operation(
            this.hideTitle or that.hideTitle,
            this.hideContent or that.hideContent,
            this.hideLargeImage or that.hideLargeImage
        )

        companion object {
            fun Rule.Actions.toOperation() = Operation(hideTitle, hideContent, hideLargeImage)
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        scope.launch {
            val operations = getActions(
                sbn.packageName,
                sbn.isOngoing,
                sbn.notification.hasProgressBar
            )

            if (operations.isEmpty()) return@launch

            val operation = operations.fold(Operation()) { acc, it ->
                acc or it.toOperation()
            }

            val notification = replaceNotification(sbn.notification, operation)

            with(NotificationManagerCompat.from(this@NotificationListener)) {
                if (ActivityCompat.checkSelfPermission(
                        this@NotificationListener, android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) return@with

                notify(sbn.id, notification)
                cancelNotification(sbn.key)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }
}