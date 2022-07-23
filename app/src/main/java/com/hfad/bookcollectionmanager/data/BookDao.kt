package com.hfad.bookcollectionmanager.data

import androidx.lifecycle.LiveData
import androidx.room.*

//Define Data Access Object CRUD AND Custom Queries
@Dao
interface BookDao {
    @Insert
    suspend fun insert(book : Book)

    @Update
    suspend fun update(book : Book)

    @Delete
    suspend fun delete(book:Book)

    @Query("SELECT * FROM book_table WHERE bookId = :bookId")
    fun get(bookId:Long) : LiveData<Book>

    @Query("SELECT * FROM book_table WHERE bookId = :bookId")
    fun getBook(bookId:Long) : Book

    @Query("SELECT * FROM book_table ORDER BY bookId DESC")
    fun getAll() : LiveData<List<Book>>

}