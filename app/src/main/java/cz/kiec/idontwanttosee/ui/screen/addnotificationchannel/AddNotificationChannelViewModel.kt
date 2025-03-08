package cz.kiec.idontwanttosee.ui.screen.addnotificationchannel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kiec.idontwanttosee.dbs.entity.NotificationChannel
import cz.kiec.idontwanttosee.dbs.repository.NotificationChannelRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddNotificationChannelViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NotificationChannelRepository
) : ViewModel() {
    private val _uiState = savedStateHandle.getMutableStateFlow(
        ADD_NOTIFICATION_CHANNEL_HANDLE,
        AddNotificationChannelUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun setName(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun save() {
        val state = _uiState.value

        viewModelScope.launch {
            repository.insert(state.toModel())
        }
    }

    private fun AddNotificationChannelUiState.toModel() =
        NotificationChannel(0, name)

    companion object {
        private const val ADD_NOTIFICATION_CHANNEL_HANDLE = "ADD_NOTIFICATION_CHANNEL_HANDLE"
    }
}