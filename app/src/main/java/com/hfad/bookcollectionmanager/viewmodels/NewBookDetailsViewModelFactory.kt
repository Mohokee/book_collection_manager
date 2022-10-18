package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.bookcollectionmanager.data.BookDao

class NewBookDetailsViewModelFactory(private val title:String,
                                     private val author:String,
                                     private val publishDate:String,
                                     private val isbn:String,
                                     private val subject:String,
                                     private val dao: BookDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewBookDetailsViewModel::class.java)){
            return NewBookDetailsViewModel(title,author,publishDate,isbn, subject,dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")

    }
}