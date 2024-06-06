package com.example.contactsbilldu.ui.home.contacts

import androidx.lifecycle.viewModelScope
import com.example.contactsbilldu.core.BaseViewModel
import com.example.contactsbilldu.data.repository.ContactRepository
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val contactRepository: ContactRepository
) : BaseViewModel<
        ContactsViewModel.ContactsUiState,
        ContactsViewModel.ContactsEvent,
        BaseViewModel.Effect>() {

    override val initialUiState = ContactsUiState()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.loadContacts().collectLatest {
                updateUiState(uiState.value.copy(
                    contacts = it
                ))
            }
        }
    }
    override fun handleEvent(event: Event) {
        when (event) {
            is ContactsEvent.ContactDeleteClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    contactRepository.deleteContactById(event.id)
                }
            }
            is ContactsEvent.ContactFavoriteClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    contactRepository.update(
                        event.contact.copy(
                            isFavorite = !event.contact.isFavorite
                        )
                    )
                }
            }
        }
    }

    data class ContactsUiState(
        val contacts: List<Contact> = emptyList(),
    ): UiState

    sealed class ContactsEvent: Event {
        class ContactDeleteClicked(val id: Int) : ContactsEvent()
        class ContactFavoriteClicked(val contact: Contact) : ContactsEvent()
    }
}