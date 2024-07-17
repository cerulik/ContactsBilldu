package com.example.contactsbilldu.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        UiState: BaseViewModel.UiState, Event: BaseViewModel.Event, Effect: BaseViewModel.Effect
        > : ViewModel() {

    protected abstract val initialUiState: UiState

    private val _uiState by lazy { MutableStateFlow(initialUiState) }
    val uiState: StateFlow<UiState> by lazy { _uiState }

    private val _effect by lazy { Channel<Effect>() }
    val effect by lazy { _effect.receiveAsFlow() }

    fun onEvent(event: BaseViewModel.Event) {
        viewModelScope.launch {
            handleEvent(event)
        }
    }

    abstract fun handleEvent(event: BaseViewModel.Event)

    protected fun updateUiState(uiState: UiState) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = uiState
        }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    interface UiState
    interface Event
    interface Effect

}