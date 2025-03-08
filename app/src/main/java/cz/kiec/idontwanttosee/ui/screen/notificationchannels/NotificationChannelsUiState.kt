package cz.kiec.idontwanttosee.ui.screen.notificationchannels

data class NotificationChannelsUiState(
    val channels: List<NotificationChannel> = listOf(),
) {
    data class NotificationChannel(
        val id: Long,
        val name: String,
        // TODO
    )
}