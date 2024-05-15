package cz.kiec.idontwanttosee.uiState

data class RuleUiState(
    val id: Long,
    val packageName: String,
    val ignoreOngoing: Boolean,
    val ignoreWithProgressBar: Boolean,
    val hideTitle: Boolean,
    val hideContent: Boolean,
    val hideLargeImage: Boolean,
)
