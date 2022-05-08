package ru.mirea.istornikov.mireaproject.ui.stories.database

import android.app.Application
import androidx.room.Room


class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "storydatabase").allowMainThreadQueries().build()
    }
}

