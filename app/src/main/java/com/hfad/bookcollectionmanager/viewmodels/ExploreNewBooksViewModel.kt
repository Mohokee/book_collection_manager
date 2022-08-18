package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExploreNewBooksViewModel : ViewModel() {

    //Private status variable of most recent API request, read and write
    private val _status = MutableLiveData<String>()

    //Public read only status variable
    val status : LiveData<String>
        get() = _status
    


}