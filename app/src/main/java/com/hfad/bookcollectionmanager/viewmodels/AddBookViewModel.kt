package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.Book
import com.hfad.bookcollectionmanager.data.BookDao
import kotlinx.coroutines.launch

/*
*This View Model contains the logic that allows a user
* to add a book to the book database
*
* */

class AddBookViewModel(val dao:BookDao) : ViewModel() {
    var addTitle = ""
    var addAuthor = ""
    var addTags = ""
    var addDescription = ""

    //create a mutable livedata value to observe from AddBookFragment so it knows when to Toast
    private val _status = MutableLiveData<Boolean?>()
    //read only public version of status val
    val status : LiveData<Boolean?>
        get() = _status


    fun addBook() {
        viewModelScope.launch {
            val book = Book(title = addTitle, author = addAuthor,
                tags = addTags, description = addDescription)
            dao.insert(book)
        }
        //It's time for the fragment to Toast
        _status.value = true
    }
    //reset status val back to null
    fun resetStatus() {
        _status.value = null
    }
}