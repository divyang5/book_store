package com.example.bookstore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookstore.model.Books


@Database(entities = [Books::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "room_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}