package cz.kiec.idontwanttosee.ui.screen.notificationchannels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.dbs.entity.NotificationChannel
import cz.kiec.idontwanttosee.dbs.repository.NotificationChannelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationChannelsViewModel(private val repository: NotificationChannelRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(NotificationChannelsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.data.collect { channelList ->
                val channels = channelList.map { channel ->
                    channel.toUiState()
                }

                _uiState.update { it.copy(channels = channels) }
            }
        }
    }

    private fun NotificationChannel.toUiState() =
        NotificationChannelsUiState.NotificationChannel(
            id,
            name
        )

    private fun NotificationChannelsUiState.NotificationChannel.toModel() =
        NotificationChannel(id, name)
}