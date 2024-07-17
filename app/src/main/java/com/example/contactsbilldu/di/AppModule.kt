package com.example.contactsbilldu.di

import android.app.Application
import androidx.room.Room
import com.example.contactsbilldu.data.repository.ContactRepository
import com.example.contactsbilldu.data.source.local.ContactDatabase
import com.example.contactsbilldu.data.source.local.ContactLocalSource
import com.example.contactsbilldu.data.source.local.dao.ContactDao
import com.example.contactsbilldu.ui.addcontact.AddContactViewModel
import com.example.contactsbilldu.ui.home.contacts.ContactsViewModel
import com.example.contactsbilldu.ui.home.favorites.FavoritesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideContactDao(get()) }
    single { ContactLocalSource(get()) }
    single { ContactRepository(get()) }
    viewModel { ContactsViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { parameters -> AddContactViewModel(parameters.get(), get()) }
}

fun provideDatabase(application: Application): ContactDatabase {
    return Room.databaseBuilder(application, ContactDatabase::class.java, "contact_database")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideContactDao(database: ContactDatabase): ContactDao {
    return database.contactDao()
}