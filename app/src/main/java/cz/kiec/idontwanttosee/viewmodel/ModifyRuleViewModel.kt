package cz.kiec.idontwanttosee.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.MutableSaveStateFlow
import cz.kiec.idontwanttosee.repository.RuleRepository
import cz.kiec.idontwanttosee.repository.dbs.entity.Rule
import cz.kiec.idontwanttosee.uiState.RuleEditUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ModifyRuleViewModel(
    val id: Long,
    savedStateHandle: SavedStateHandle,
    private val repository: RuleRepository
) : ViewModel() {
    private val _uiState = MutableSaveStateFlow(
        savedStateHandle,
        RULE_EDIT_HANDLE
    ) { RuleEditUiState() }
    val uiState: StateFlow<RuleEditUiState> = _uiState.flow

    init {
        viewModelScope.launch {
            // Process recreation
            if (!savedStateHandle.contains(RULE_EDIT_HANDLE)) {
                _uiState.value = repository.select(id).first().toUiState()
            }

            uiState.collect { state ->
                repository.update(state.toModel())
            }
        }
    }

    fun setPackageName(value: String) {
        _uiState.value = _uiState.value.copy(packageName = value)
    }

    fun setIgnoreOngoing(value: Boolean) {
        _uiState.value = _uiState.value.copy(ignoreOngoing = value)
    }

    fun setIgnoreWithProgressBar(value: Boolean) {
        _uiState.value = _uiState.value.copy(ignoreWithProgressBar = value)
    }

    fun setHideTitle(value: Boolean) {
        _uiState.value = _uiState.value.copy(hideTitle = value)
    }

    fun setHideContent(value: Boolean) {
        _uiState.value = _uiState.value.copy(hideContent = value)
    }

    fun setHideLargeImage(value: Boolean) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(hideLargeImage = value)
    }

    private fun Rule.toUiState() =
        RuleEditUiState(
            filters.packageName,
            filters.ignoreOngoing,
            filters.ignoreWithProgressBar,
            actions.hideTitle,
            actions.hideContent,
            actions.hideLargeImage
        )

    private fun RuleEditUiState.toModel() =
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

    companion object {
        val RULE_EDIT_HANDLE = "RULE_EDIT"
    }
}