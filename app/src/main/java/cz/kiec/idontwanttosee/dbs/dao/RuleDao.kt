package cz.kiec.idontwanttosee.dbs.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cz.kiec.idontwanttosee.dbs.entity.Rule
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleDao {
    @Query("SELECT * FROM rule")
    fun getAll(): Flow<List<Rule>>

    @Insert
    suspend fun insert(rule: Rule): Long

    @Update
    suspend fun update(rule: Rule)

    @Query("SELECT * FROM rule WHERE id=:id")
    suspend fun selectOnce(id: Long): Rule

    @Query("SELECT * FROM rule WHERE id=:id")
    fun select(id: Long): Flow<Rule>

    @Delete
    suspend fun delete(rule: Rule)

    @Query("SELECT * FROM rule WHERE filter_package_name = :packageName")
    suspend fun select(packageName: String): List<Rule>
}