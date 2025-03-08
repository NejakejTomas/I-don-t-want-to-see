package cz.kiec.idontwanttosee.ui.screen.addrule

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.dbs.entity.Rule
import cz.kiec.idontwanttosee.dbs.repository.RuleRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddRuleViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RuleRepository
) : ViewModel() {
    private val _uiState = savedStateHandle.getMutableStateFlow<AddRuleUiState>(
        ADD_RULE_HANDLE,
        AddRuleUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun setPackageName(value: String) {
        _uiState.value = _uiState.value.copy(packageName = value)
    }

    fun setIsEnabled(value: Boolean) {
        _uiState.value = _uiState.value.copy(isEnabled = value)
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

    private fun AddRuleUiState.toModel() =
        Rule(
            0,
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

    companion object {
        private const val ADD_RULE_HANDLE = "ADD_RULE"
    }
}