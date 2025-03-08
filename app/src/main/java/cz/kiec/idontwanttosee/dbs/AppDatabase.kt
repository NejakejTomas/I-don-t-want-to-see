package cz.kiec.idontwanttosee.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.kiec.idontwanttosee.dbs.dao.NotificationChannelDao
import cz.kiec.idontwanttosee.dbs.dao.RuleDao
import cz.kiec.idontwanttosee.dbs.entity.NotificationChannel
import cz.kiec.idontwanttosee.dbs.entity.Rule

@Database(entities = [Rule::class, NotificationChannel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ruleDao(): RuleDao
    abstract fun notificationChannelDao(): NotificationChannelDao
}