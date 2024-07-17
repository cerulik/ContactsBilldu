package com.example.contactsbilldu.data.source.local

import com.example.contactsbilldu.data.source.local.dao.ContactDao
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.flow.Flow

class ContactLocalSource(
    private val contactDao: ContactDao
) {
    fun getContacts(offset: Int, limit: Int): List<Contact> {
        return contactDao.getContacts(offset, limit)
    }

    fun searchContacts(query: String, offset: Int, limit: Int): List<Contact> {
        return contactDao.searchContacts(query, offset, limit)
    }

    fun getFavoriteContacts(): Flow<List<Contact>> {
        return contactDao.getFavoriteContacts()
    }

    fun getContactById(contactId: Int): Contact? {
        return contactDao.getContactById(contactId)
    }

    suspend fun addContact(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun updateContact(contact: Contact) {
        contactDao.update(contact)
    }

    suspend fun deleteContactById(contactId: Int) {
        contactDao.deleteById(contactId)
    }
}