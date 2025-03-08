package cz.kiec.idontwanttosee.dbs.repository

import cz.kiec.idontwanttosee.dbs.dao.RuleDao
import cz.kiec.idontwanttosee.dbs.entity.Rule
import kotlinx.coroutines.flow.Flow

class RuleRepository(private val ruleDao: RuleDao) {
    fun select(id: Long): Flow<Rule> = ruleDao.select(id)
    val data: Flow<List<Rule>> = ruleDao.getAll()
    suspend fun insert(rule: Rule) = ruleDao.insert(rule)
    suspend fun update(rule: Rule) = ruleDao.update(rule)
    suspend fun selectActions(id: Long) = ruleDao.selectOnce(id)
    suspend fun delete(rule: Rule) = ruleDao.delete(rule)
    suspend fun selectActions(
        packageName: String, isOngoing: Boolean, hasProgressBar: Boolean
    ) = ruleDao.select(packageName).filter {
        if (!it.filters.isEnabled) return@filter false
        if (isOngoing && it.filters.ignoreOngoing) return@filter false
        if (hasProgressBar && it.filters.ignoreWithProgressBar) return@filter false

        return@filter true
    }.map { it.actions }
}