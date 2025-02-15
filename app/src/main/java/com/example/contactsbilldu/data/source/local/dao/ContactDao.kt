package com.example.contactsbilldu.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts LIMIT :limit OFFSET :offset")
    fun getContacts(offset: Int, limit: Int): List<Contact>

    @Query("SELECT * FROM contacts WHERE firstName LIKE '%' || :query || '%' " +
            "OR lastName LIKE '%' || :query || '%' " +
            "OR phoneNumber LIKE '%' || :query || '%' " +
            "LIMIT :limit OFFSET :offset")
    fun searchContacts(query: String, offset: Int, limit: Int): List<Contact>

    @Query("SELECT * FROM contacts WHERE isFavorite = 1")
    fun getFavoriteContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContactById(contactId: Int): Contact?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("DELETE FROM contacts WHERE id = :contactId")
    suspend fun deleteById(contactId: Int)
}