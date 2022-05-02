package ru.mirea.istornikov.room

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
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database").allowMainThreadQueries().build()
    }
}