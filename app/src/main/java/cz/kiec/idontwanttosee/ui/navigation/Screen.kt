package cz.kiec.idontwanttosee.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Rules : Screen

    @Serializable
    data object AddRule

    @Serializable
    data class ModifyRule(val id: Long) : Screen

    @Serializable
    data object NotificationChannels : Screen

    @Serializable
    data object AddNotificationChannel : Screen

    @Serializable
    data class ModifyNotificationChannel(val id: Long) : Screen
}