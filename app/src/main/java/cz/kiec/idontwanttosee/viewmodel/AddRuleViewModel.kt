package cz.kiec.idontwanttosee.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.MutableSaveStateFlow
import cz.kiec.idontwanttosee.repository.RuleRepository
import cz.kiec.idontwanttosee.repository.dbs.entity.Rule
import cz.kiec.idontwanttosee.uiState.RuleEditUiState
import kotlinx.coroutines.launch

class AddRuleViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RuleRepository
) : ViewModel() {
    private val _uiState: MutableSaveStateFlow<RuleEditUiState> =
        MutableSaveStateFlow(savedStateHandle, ADD_RULE_HANDLE, ::RuleEditUiState)

    val uiState = _uiState.flow

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

    fun setHideLargeImage(value: Boolean) {
        _uiState.value = _uiState.value.copy(hideLargeImage = value)
    }

    fun save() {
        val state = _uiState.value

        viewModelScope.launch {
            repository.insert(state.toModel())
        }
    }

    private fun RuleEditUiState.toModel() =
        Rule(
            0,
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
        private const val ADD_RULE_HANDLE = "ADD_RULE"
    }
}