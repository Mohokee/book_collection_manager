package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.BookDao
import kotlinx.coroutines.launch

class BookDetailsViewModel(bookId:Long, val dao : BookDao): ViewModel() {
    val book = dao.get(bookId)

    fun deleteBook(){
        viewModelScope.launch{
            dao.delete(book.value!!)
        }
    }
}