package com.hfad.bookcollectionmanager.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.bookcollectionmanager.data.BookDao

class AddBookViewModelFactory(private val dao: BookDao)
    : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
            if (modelClass.isAssignableFrom(AddBookViewModel::class.java)) {
                return AddBookViewModel(dao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
