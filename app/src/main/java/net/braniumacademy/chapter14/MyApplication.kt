package net.braniumacademy.chapter14

import android.app.Application
import net.braniumacademy.chapter14.data.database.AppDatabase
import net.braniumacademy.chapter14.data.repository.ProfileRepositoryImpl
import net.braniumacademy.chapter14.data.repository.UserRepositoryImpl

class MyApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(applicationContext) }
    val repository by lazy { UserRepositoryImpl(database.getUserDao()) }
    val profileRepository by lazy { ProfileRepositoryImpl(database.getProfileDao()) }
}