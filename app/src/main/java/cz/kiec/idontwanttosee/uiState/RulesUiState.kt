package cz.kiec.idontwanttosee.uiState

data class RulesUiState(
    val rules: List<RuleUiState> = listOf(),
    val expandedRules: List<RuleUiState> = listOf(),
    val editDialogForRule: RuleUiState? = null
)