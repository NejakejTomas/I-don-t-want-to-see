package cz.kiec.idontwanttosee.repository

import cz.kiec.idontwanttosee.repository.dbs.dao.RuleDao
import cz.kiec.idontwanttosee.repository.dbs.entity.Rule
import kotlinx.coroutines.flow.Flow

class RuleRepositoryImpl(private val ruleDao: RuleDao) : RuleRepository {
    override fun select(id: Long): Flow<Rule> = ruleDao.select(id)
    override val data: Flow<List<Rule>> = ruleDao.getAll()
    override suspend fun insert(rule: Rule) = ruleDao.insert(rule)
    override suspend fun update(rule: Rule) = ruleDao.update(rule)
    override suspend fun selectActions(id: Long) = ruleDao.selectOnce(id)
    override suspend fun delete(rule: Rule) = ruleDao.delete(rule)
    override suspend fun selectActions(
        packageName: String, isOngoing: Boolean, hasProgressBar: Boolean
    ) = ruleDao.select(packageName).filter {
        if (isOngoing && it.filters.ignoreOngoing) return@filter false
        if (hasProgressBar && it.filters.ignoreWithProgressBar) return@filter false

        return@filter true
    }.map { it.actions }
}