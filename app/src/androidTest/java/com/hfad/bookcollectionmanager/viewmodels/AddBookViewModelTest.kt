package com.hfad.bookcollectionmanager.viewmodels

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hfad.bookcollectionmanager.data.BookDao
import com.hfad.bookcollectionmanager.data.BookDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddBookViewModelTest {
    private lateinit var dao : BookDao
    private lateinit var db : BookDatabase
    private lateinit var viewModel : AddBookViewModel


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,BookDatabase::class.java).build()
        dao = db.bookDao
        viewModel = AddBookViewModel(dao)
    }

    @After
    fun tearDown() {
        db.close()
    }


    //Test adding a book to the database, check if the author is the same as entered
    @Test
    @Throws(Exception::class)
    fun addBook() {
        runBlocking {
            viewModel.addAuthor = "Salleee"
            viewModel.addBook()
            val book = dao.getBook(1)
            assertThat(book.author, equalTo("Salleee"))
        }

    }

}