package cz.kiec.idontwanttosee

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }

        notificationChannels.forEach {
            createNotificationChannel(it.first, it.second)
        }
    }

    private fun createNotificationChannel(id: String, importanceLevel: Int) {
        val name = getString(R.string.notification_channel_name)
        val descriptionText = getString(R.string.notification_channel_description)

        val channel = NotificationChannel(id, name, importanceLevel).apply {
            description = descriptionText
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        val notificationChannels = listOf(
            "NONE" to NotificationManager.IMPORTANCE_NONE,
            "MIN" to NotificationManager.IMPORTANCE_MIN,
            "LOW" to NotificationManager.IMPORTANCE_LOW,
            "DEFAULT" to NotificationManager.IMPORTANCE_DEFAULT,
            "HIGH" to NotificationManager.IMPORTANCE_HIGH,
            "MAX" to NotificationManager.IMPORTANCE_MAX,
        )
    }
}