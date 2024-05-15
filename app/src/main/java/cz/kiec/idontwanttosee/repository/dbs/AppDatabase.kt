package cz.kiec.idontwanttosee.repository.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.kiec.idontwanttosee.repository.dbs.dao.RuleDao
import cz.kiec.idontwanttosee.repository.dbs.entity.Rule

@Database(entities = [Rule::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ruleDao(): RuleDao
}