package com.example.contactsbilldu.ui.home.favorites

import androidx.lifecycle.viewModelScope
import com.example.contactsbilldu.core.BaseViewModel
import com.example.contactsbilldu.data.repository.ContactRepository
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val contactRepository: ContactRepository
) : BaseViewModel<
        FavoritesViewModel.FavoritesUiState,
        BaseViewModel.Event,
        BaseViewModel.Effect>() {

    override val initialUiState = FavoritesUiState()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.loadFavoriteContacts().collectLatest {
                updateUiState(uiState.value.copy(
                    favoriteContacts = it
                ))
            }
        }
    }
    override fun handleEvent(event: Event) {}

    data class FavoritesUiState(
        val favoriteContacts: List<Contact> = emptyList(),
    ): UiState
}