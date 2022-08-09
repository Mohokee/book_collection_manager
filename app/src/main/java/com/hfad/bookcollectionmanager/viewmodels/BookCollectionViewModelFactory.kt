package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.bookcollectionmanager.data.BookDao

class BookCollectionViewModelFactory(private val dao: BookDao)
    : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
        if (modelClass.isAssignableFrom(BookCollectionViewModel::class.java)) {
            return BookCollectionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}