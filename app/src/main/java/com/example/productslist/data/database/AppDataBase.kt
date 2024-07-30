package com.example.productslist.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productslist.di.ApplicationScope

@Database(entities = [ProductDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao(): AppDataBaseDao

    companion object {
        private const val DB_NAME = "ProductsDB"
        private val LOCK: Any = Any()
        private var INSTANCE: AppDataBase? = null

        fun createInstance(application: Application): AppDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application, AppDataBase::class.java, DB_NAME)
                    .build()
                INSTANCE = db
                return db
            }

        }
    }
}