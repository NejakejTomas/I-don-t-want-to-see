package cz.kiec.idontwanttosee.uiState

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RuleEditUiState(
    val packageName: String = "",
    val ignoreOngoing: Boolean = false,
    val ignoreWithProgressBar: Boolean = false,
    val hideTitle: Boolean = false,
    val hideContent: Boolean = false,
    val hideLargeImage: Boolean = false,
) : Parcelable
