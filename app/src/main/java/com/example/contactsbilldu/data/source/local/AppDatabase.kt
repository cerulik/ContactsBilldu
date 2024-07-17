package com.example.contactsbilldu.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactsbilldu.data.source.local.dao.ContactDao
import com.example.contactsbilldu.data.source.local.entity.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}