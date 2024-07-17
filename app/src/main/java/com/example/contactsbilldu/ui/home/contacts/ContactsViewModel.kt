package com.example.contactsbilldu.ui.home.contacts

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.contactsbilldu.core.BaseViewModel
import com.example.contactsbilldu.data.repository.ContactRepository
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ContactsViewModel(
    private val contactRepository: ContactRepository
) : BaseViewModel<
        ContactsViewModel.ContactsUiState,
        ContactsViewModel.ContactsEvent,
        BaseViewModel.Effect>() {

    override val initialUiState = ContactsUiState()
    private val refreshTrigger = MutableStateFlow(false)
    private val searchQuery = MutableStateFlow("")
    val contacts: Flow<PagingData<Contact>> = combine(searchQuery, refreshTrigger) { query, _ ->
        query
    }.debounce(300)
        .flatMapLatest {
            contactRepository.loadContacts(it).cachedIn(viewModelScope)
        }

    override fun handleEvent(event: Event) {
        viewModelScope.launch {
            when (event) {
                is ContactsEvent.ContactDeleteClicked -> {
                    contactRepository.deleteContactById(event.id)
                    refreshContacts()
                }

                is ContactsEvent.ContactFavoriteClicked -> {
                    contactRepository.updateContact(
                        event.contact.copy(
                            isFavorite = !event.contact.isFavorite
                        )
                    )
                    refreshContacts()
                }

                is ContactsEvent.SearchQueryChanged -> {
                    searchQuery.value = event.query
                }
            }
        }
    }

    private fun refreshContacts() {
        refreshTrigger.value = refreshTrigger.value.not()
    }

    class ContactsUiState: UiState

    sealed class ContactsEvent: Event {
        class ContactDeleteClicked(val id: Int) : ContactsEvent()
        class ContactFavoriteClicked(val contact: Contact) : ContactsEvent()
        class SearchQueryChanged(val query: String) : ContactsEvent()
    }
}