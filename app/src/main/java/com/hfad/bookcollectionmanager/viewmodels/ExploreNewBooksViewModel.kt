package com.hfad.bookcollectionmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.Doc
import com.hfad.bookcollectionmanager.network.OpenLibraryApi
import kotlinx.coroutines.launch

class ExploreNewBooksViewModel : ViewModel() {

    //val _docs : MutableLiveData<List<Doc>> = MutableLiveData<List<Doc>>()
    val _docs = MutableLiveData<List<Doc>>().also {
        viewModelScope.launch {
            try {
                //"search.json?author=satter&fields=title%20author_name%20subject%20publish_date%20isbn"
                /*val listResult = OpenLibraryApi.retrofitService.getBooks("search.json?author=satter")
                 _status.value = "Success: ${listResult.body()?.docs?.get(0)} Books Retrieved"*/
                val listResult = OpenLibraryApi.retrofitService.getBooks("satter")
                it.value = listResult.body()?.docs
                _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"



            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
    val docs : LiveData<List<Doc>>
        get() = _docs



    //Private status variable of most recent API request, read and write
    private val _status = MutableLiveData<String>()

    //Create a read/write title value for the onClick listener
    private val _navigateToDoc = MutableLiveData<String?>()

    //This is the read-only version of title
    val navigateToBook: LiveData<String?>
        get() = _navigateToDoc

    //Public read only status variable
    val status : LiveData<String>
        get() = _status




    private fun getBooksFromNet(){
        viewModelScope.launch{
            try {
                //"search.json?author=satter&fields=title%20author_name%20subject%20publish_date%20isbn"
                /*val listResult = OpenLibraryApi.retrofitService.getBooks("search.json?author=satter")
                 _status.value = "Success: ${listResult.body()?.docs?.get(0)} Books Retrieved"*/
                val listResult = OpenLibraryApi.retrofitService.getBooks("satter")
                //_status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
                _docs.postValue(listResult.body()!!.docs)

            } catch(e:Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }



    /*init{
        getBooksTest()
        println(docs)
    }*/

    //When a book is clicked, send the book id to the book details fragment
    fun docClicked(title: String){
        _navigateToDoc.value = title
    }


}