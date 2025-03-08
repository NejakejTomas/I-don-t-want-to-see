package cz.kiec.idontwanttosee.ui.screen.addrule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddRuleUiState(
    val packageName: String = "",
    val isEnabled: Boolean = true,
    val ignoreOngoing: Boolean = false,
    val ignoreWithProgressBar: Boolean = false,
    val hideTitle: Boolean = false,
    val hideContent: Boolean = false,
    val hideLargeImage: Boolean = false,
) : Parcelable