package com.hfad.bookcollectionmanager.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BookDatabaseTest {
    private lateinit var dao : BookDao
    private lateinit var db : BookDatabase

    var book = Book(
        1,
        "Testing 4 Dummies",
        "Dee Ummy",
        "testing, learning code",
        "Learn to do unit tests"
    )

    @Before
    fun setUpDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,BookDatabase::class.java).build()
        dao = db.bookDao
    }

    @After
    @Throws(IOException::class)
    fun tearDownDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadBookTest() {
        //Test suspended function insert
        runBlocking {
            dao.insert(book)
            //Get the book from the database
            val find = dao.getBook(1)
            // Check if it's equal to the original book
            assertThat(find, equalTo(book))
        }

    }

    @Test
    fun deleteBookTest(){
        runBlocking {
            dao.insert(book)

            var find = dao.getBook(1)
            dao.delete(find)
            find = dao.getBook(1)
            assertThat(find, `is`(nullValue()))
        }
    }

    @Test
    fun updateBookTest(){
        runBlocking {
            dao.insert(book)
            book.author = "Janice"
            dao.update(book)
            val find = dao.getBook(1)
            assertThat(find.author, equalTo("Janice"))
        }
    }

    }


