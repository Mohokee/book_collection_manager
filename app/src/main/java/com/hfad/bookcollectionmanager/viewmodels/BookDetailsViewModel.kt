package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.BookDao
import kotlinx.coroutines.launch

class BookDetailsViewModel(bookId:Long, val dao : BookDao): ViewModel() {
    val book = dao.get(bookId)
    /**
     * Create a mutable livedata value to observe so it knows when to Toast and navigate back to
     * the book collection page
     * */
    private val _status = MutableLiveData<Boolean?>()

    /**
     * Read only public version of status val
     */
    val status : LiveData<Boolean?>
        get() = _status

    /**
     * Delete the book from the room database
     */
    fun deleteBook(){
        viewModelScope.launch{
            dao.delete(book.value!!)
        }
        /**
         * Status is true, so it's time to toast
         */
        _status.value = true
    }

    /**
     * Reset Toast status livedata to null
     */
    fun resetStatus(){
        _status.value = null
    }
}