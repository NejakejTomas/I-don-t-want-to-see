package cz.kiec.idontwanttosee.dbs.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cz.kiec.idontwanttosee.dbs.entity.NotificationChannel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationChannelDao {
    @Query("SELECT * FROM notification_channel")
    fun getAll(): Flow<List<NotificationChannel>>

    @Insert
    suspend fun insert(rule: NotificationChannel): Long

    @Update
    suspend fun update(rule: NotificationChannel)

}