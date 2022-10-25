package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.Book
import com.hfad.bookcollectionmanager.data.BookDao
import kotlinx.coroutines.launch


class NewBookDetailsViewModel(
    val title: String,
    val author: String,
    val publishDate: String,
    val isbn: String,
    val subject: String,
    val dao: BookDao
) : ViewModel() {

    /**
     * Create a mutable livedata value to observe from AddBookFragment so it knows when to Toast
     */
    private val _status = MutableLiveData<Boolean?>()

    /**
     * Read only public version of status val
     */
    val status : LiveData<Boolean?>
        get() = _status

    /**
     * Add book to Room database
     */
    fun addBook() {
        viewModelScope.launch {
            val book = Book(title = title, author = author,
                tags = subject, description = "ISBN: $isbn \n Date Published: $publishDate ")
            dao.insert(book)
        }
        /**
         * It's time for the fragment to Toast
         */
        _status.value = true
    }
    /**
     * Reset status val back to null
     */

    fun resetStatus() {
        _status.value = null
    }

}