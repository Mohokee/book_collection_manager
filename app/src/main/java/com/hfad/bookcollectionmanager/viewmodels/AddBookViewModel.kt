package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.Book
import com.hfad.bookcollectionmanager.data.BookDao
import kotlinx.coroutines.launch

class AddBookViewModel(val dao:BookDao) : ViewModel() {
    var addTitle = ""
    var addAuthor = ""
    var addTags = ""
    var addDescription = ""

    fun addBook() {
        viewModelScope.launch {
            val book = Book(title = addTitle, author = addAuthor,
                tags = addTags, description = addDescription)
            dao.insert(book)
        }
    }
}