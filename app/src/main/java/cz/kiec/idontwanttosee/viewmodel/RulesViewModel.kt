package cz.kiec.idontwanttosee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.repository.RuleRepository
import cz.kiec.idontwanttosee.repository.dbs.entity.Rule
import cz.kiec.idontwanttosee.uiState.RuleUiState
import cz.kiec.idontwanttosee.uiState.RulesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RulesViewModel(private val repository: RuleRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RulesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.data.collect { ruleList ->
                val rules = ruleList.map { rule ->
                    rule.toUiState()
                }

                _uiState.update { it.copy(rules = rules) }
            }
        }
    }

    fun deleteRule(rule: RuleUiState) {
        viewModelScope.launch {
            repository.delete(rule.toModel())
        }
    }

    fun onClick(rule: RuleUiState) {
        _uiState.update {
            return@update if (it.expandedRules.contains(rule)) it.copy(
                expandedRules = it.expandedRules.minus(
                    rule
                )
            )
            else it.copy(expandedRules = it.expandedRules.plus(rule))
        }
    }

    fun onLongClick(rule: RuleUiState) {
        _uiState.update { it.copy(editDialogForRule = rule) }
    }

    fun hideDialog() {
        _uiState.update { it.copy(editDialogForRule = null) }
    }

    private fun Rule.toUiState() =
        RuleUiState(
            id,
            filters.packageName,
            filters.ignoreOngoing,
            filters.ignoreWithProgressBar,
            actions.hideTitle,
            actions.hideContent,
            actions.hideLargeImage
        )

    private fun RuleUiState.toModel() =
        Rule(
            id,
            Rule.Filters(
                packageName,
                ignoreOngoing,
                ignoreWithProgressBar,
            ),
            Rule.Actions(
                hideTitle,
                hideContent,
                hideLargeImage,
            )
        )
}