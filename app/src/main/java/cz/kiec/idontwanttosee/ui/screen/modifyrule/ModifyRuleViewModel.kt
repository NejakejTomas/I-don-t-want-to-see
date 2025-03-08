package cz.kiec.idontwanttosee.ui.screen.modifyrule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.dbs.entity.Rule
import cz.kiec.idontwanttosee.dbs.repository.RuleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ModifyRuleViewModel(
    val id: Long,
    private val repository: RuleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ModifyRuleUiState())
    val uiState: StateFlow<ModifyRuleUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = repository.select(id).first().toUiState()

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
        ModifyRuleUiState(
            filters.packageName,
            filters.ignoreOngoing,
            filters.ignoreWithProgressBar,
            actions.hideTitle,
            actions.hideContent,
            actions.hideLargeImage
        )

    private fun ModifyRuleUiState.toModel() =
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