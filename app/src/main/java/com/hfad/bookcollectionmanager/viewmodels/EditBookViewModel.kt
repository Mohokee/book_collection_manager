package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.BookDao
import kotlinx.coroutines.launch

class EditBookViewModel(bookId: Long, val dao: BookDao): ViewModel() {
    //private backing property to see when Toasting and
    //navigation could occur
    private val _status = MutableLiveData<Boolean?>()
    //public read only version of status val
    val status : LiveData<Boolean?>
        get() = _status

    val book = dao.get(bookId)

    fun updateBook(){
        viewModelScope.launch{
            dao.update(book.value!!)
        }
        //It's time for the fragment to Toast and navigate back to the book collection fragment
        _status.value = true
    }

    //reset status val back to null
    fun resetStatus(){
        _status.value = null
    }
}