package com.example.contactsbilldu.data.repository

import com.example.contactsbilldu.data.source.local.ContactLocalSource
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.flow.Flow

class ContactRepository(
    private val localSource: ContactLocalSource
) {
    fun loadContacts() : Flow<List<Contact>> {
        return localSource.getContacts()
    }

    fun loadFavoriteContacts() : Flow<List<Contact>> {
        return localSource.getFavoriteContacts()
    }
    suspend fun addContact(contact: Contact) {
        localSource.addContact(contact)
    }

    suspend fun update(contact: Contact) {
        localSource.updateContact(contact)
    }

    suspend fun deleteContactById(contactId: Int) {
        localSource.deleteContactById(contactId)
    }
}