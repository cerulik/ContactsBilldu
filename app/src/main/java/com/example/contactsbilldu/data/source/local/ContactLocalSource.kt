package com.example.contactsbilldu.data.source.local

import com.example.contactsbilldu.data.source.local.dao.ContactDao
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.flow.Flow

class ContactLocalSource constructor(
    private val contactDao: ContactDao
) {
    fun getContacts(): Flow<List<Contact>> {
        return contactDao.getContacts()
    }

    fun getFavoriteContacts(): Flow<List<Contact>> {
        return contactDao.getFavoriteContacts()
    }

    suspend fun addContact(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        contactDao.delete(contact)
    }
}