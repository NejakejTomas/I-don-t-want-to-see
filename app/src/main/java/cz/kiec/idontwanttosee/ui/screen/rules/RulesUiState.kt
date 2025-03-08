package cz.kiec.idontwanttosee.ui.screen.rules

data class RulesUiState(
    val rules: List<RuleUiState> = listOf(),
    val expandedRules: List<RuleUiState> = listOf(),
    val editDialogForRule: RuleUiState? = null
) {
    data class RuleUiState(
        val id: Long,
        val packageName: String,
        val isEnabled: Boolean,
        val ignoreOngoing: Boolean,
        val ignoreWithProgressBar: Boolean,
        val hideTitle: Boolean,
        val hideContent: Boolean,
        val hideLargeImage: Boolean,
    )
}