package cz.kiec.idontwanttosee.repository

import cz.kiec.idontwanttosee.repository.dbs.entity.Rule
import kotlinx.coroutines.flow.Flow

interface RuleRepository {
    val data: Flow<List<Rule>>
    fun select(id: Long): Flow<Rule>
    suspend fun insert(rule: Rule): Long
    suspend fun update(rule: Rule)
    suspend fun selectActions(id: Long): Rule
    suspend fun delete(rule: Rule)
    suspend fun selectActions(
        packageName: String,
        isOngoing: Boolean,
        hasProgressBar: Boolean
    ): List<Rule.Actions>
}