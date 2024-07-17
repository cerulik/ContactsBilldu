package com.example.contactsbilldu

import android.app.Application
import com.example.contactsbilldu.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ContactsApplication)
            modules(appModule)
        }
    }
}