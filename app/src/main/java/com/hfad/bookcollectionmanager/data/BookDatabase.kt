@file:OptIn(InternalCoroutinesApi::class)

package com.hfad.bookcollectionmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

//Creates class that creates database instance in a companion object so
//
@Database(entities = [Book::class], version = 6, exportSchema = false)
abstract class BookDatabase :RoomDatabase() {
    //define dao
    abstract val bookDao: BookDao

    //get database instance
    //abstract fun getBookDao() : BookDao

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}




