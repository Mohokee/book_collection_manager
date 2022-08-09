package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.bookcollectionmanager.data.BookDao

class BookCollectionViewModel(val dao:BookDao) : ViewModel() {
    //Get a list of all books from the dao
    val books = dao.getAll()

    //provide the book ID so that Book Collection can pass it to
    //Book Details, the read/write private and could be null
    private val _navigateToBook = MutableLiveData<Long?>()
    //This is the read-only version that other classes can access
    val navigateToBook: LiveData<Long?>
        get() = _navigateToBook

    //When a book is clicked, send the book id to the book details fragment
    fun bookClicked(bookId: Long){
        _navigateToBook.value = bookId
    }

    //Set the value to pass to detailed book view to null after
    //it has been navigated to so it's ready for future clicks
    fun bookNavigatedTo(){
        _navigateToBook.value = null
    }
}
