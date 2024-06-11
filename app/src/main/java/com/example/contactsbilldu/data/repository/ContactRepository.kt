package com.example.contactsbilldu.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.contactsbilldu.ContactPagingSource
import com.example.contactsbilldu.data.source.local.ContactLocalSource
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ContactRepository(
    private val localSource: ContactLocalSource
) {
    fun loadContacts(query: String = "") : Flow<PagingData<Contact>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ContactPagingSource(localSource, query) }
        ).flow.flowOn(Dispatchers.IO)
    }

    fun loadFavoriteContacts() : Flow<List<Contact>> {
        return localSource.getFavoriteContacts().flowOn(Dispatchers.IO)
    }

    suspend fun loadContactById(contactId: Int): Contact? = withContext(Dispatchers.IO) {
        return@withContext localSource.getContactById(contactId)
    }

    suspend fun addContact(contact: Contact) = withContext(Dispatchers.IO) {
        localSource.addContact(contact)
    }

    suspend fun updateContact(contact: Contact) = withContext(Dispatchers.IO) {
        localSource.updateContact(contact)
    }

    suspend fun deleteContactById(contactId: Int) = withContext(Dispatchers.IO) {
        localSource.deleteContactById(contactId)
    }
}