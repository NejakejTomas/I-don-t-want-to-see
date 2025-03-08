package cz.kiec.idontwanttosee.dbs.repository

import cz.kiec.idontwanttosee.dbs.dao.NotificationChannelDao
import cz.kiec.idontwanttosee.dbs.entity.NotificationChannel
import kotlinx.coroutines.flow.Flow

class NotificationChannelRepository(private val notificationChannelDao: NotificationChannelDao) {
    val data: Flow<List<NotificationChannel>> = notificationChannelDao.getAll()
    suspend fun insert(notificationChannel: NotificationChannel) =
        notificationChannelDao.insert(notificationChannel)

    suspend fun update(notificationChannel: NotificationChannel) =
        notificationChannelDao.update(notificationChannel)
}