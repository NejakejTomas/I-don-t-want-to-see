package cz.kiec.idontwanttosee.ui.screen.addnotificationchannel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddNotificationChannelUiState(
    val id: Long? = null,
    val name: String = "",
) : Parcelable