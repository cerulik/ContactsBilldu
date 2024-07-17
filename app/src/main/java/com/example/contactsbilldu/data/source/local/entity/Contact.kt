package com.example.contactsbilldu.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val countryCode: String,
    val phoneNumber: String,
    val isFavorite: Boolean = false
)