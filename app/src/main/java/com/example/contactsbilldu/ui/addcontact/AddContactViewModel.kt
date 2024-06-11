package com.example.contactsbilldu.ui.addcontact

import androidx.lifecycle.viewModelScope
import com.example.contactsbilldu.core.BaseViewModel
import com.example.contactsbilldu.data.repository.ContactRepository
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddContactViewModel(
    private val oldContactId: Int,
    private val contactRepository: ContactRepository
) : BaseViewModel<
        AddContactViewModel.AddContactUiState,
        AddContactViewModel.AddContactEvent,
        BaseViewModel.Effect>() {

    override val initialUiState = AddContactUiState(
        isContactLoading = oldContactId != -1
    )

    init {
        if (oldContactId != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                val oldContact = contactRepository.loadContactById(oldContactId)

                updateUiState(
                    uiState.value.copy(
                        oldContact = oldContact,
                        isContactLoading = false
                    )
                )
            }
        }
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is AddContactEvent.SaveContactClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    updateUiState(
                        uiState.value.copy(isContactSaving = true)
                    )

                    if (uiState.value.oldContact != null) {
                        contactRepository.updateContact(event.contact)
                    } else {
                        contactRepository.addContact(event.contact)
                    }

                    sendEffect(AddContactEffect.ContactSaved)
                }
            }
        }
    }

    data class AddContactUiState(
        val oldContact: Contact? = null,
        val isContactLoading: Boolean = false,
        val isContactSaving: Boolean = false
    ): UiState

    sealed class AddContactEvent: Event {
        class SaveContactClicked(val contact: Contact) : AddContactEvent()
    }

    sealed class AddContactEffect: Effect {
        data object ContactSaved : AddContactEffect()
    }
}