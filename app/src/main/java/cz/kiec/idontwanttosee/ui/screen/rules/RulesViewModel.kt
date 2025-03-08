package cz.kiec.idontwanttosee.ui.screen.rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.dbs.entity.Rule
import cz.kiec.idontwanttosee.dbs.repository.RuleRepository
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

    fun deleteRule(rule: RulesUiState.RuleUiState) {
        viewModelScope.launch {
            repository.delete(rule.toModel())
        }
    }

    fun onClick(rule: RulesUiState.RuleUiState) {
        _uiState.update {
            return@update if (it.expandedRules.contains(rule)) it.copy(
                expandedRules = it.expandedRules.minus(
                    rule
                )
            )
            else it.copy(expandedRules = it.expandedRules.plus(rule))
        }
    }

    fun onLongClick(rule: RulesUiState.RuleUiState) {
        _uiState.update { it.copy(editDialogForRule = rule) }
    }

    fun hideDialog() {
        _uiState.update { it.copy(editDialogForRule = null) }
    }

    private fun Rule.toUiState() =
        RulesUiState.RuleUiState(
            id,
            filters.packageName,
            filters.isEnabled,
            filters.ignoreOngoing,
            filters.ignoreWithProgressBar,
            actions.hideTitle,
            actions.hideContent,
            actions.hideLargeImage
        )

    private fun RulesUiState.RuleUiState.toModel() =
        Rule(
            id,
            Rule.Filters(
                packageName,
                isEnabled,
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