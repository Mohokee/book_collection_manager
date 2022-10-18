package com.hfad.bookcollectionmanager.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.bookcollectionmanager.data.Doc
import com.hfad.bookcollectionmanager.data.NewBooks
import com.hfad.bookcollectionmanager.network.OpenLibraryApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class ExploreNewBooksViewModel : ViewModel() {

    //Live data variable for user entered search term
    private val _searchTerm = MutableLiveData<String>("")
    val searchTerm : LiveData<String>
        get() = _searchTerm

    //Use selected chip group int ID for api information category
    private val _category = MutableLiveData<Int>()
    val category : LiveData<Int>
        get() = _category

    // Selected category chip string name
    private val _searchCategory = MutableLiveData<String>()
    val searchCategory : LiveData<String>
        get()= _searchCategory

    //Private status variable of most recent API request, read and write
    private val _status = MutableLiveData<String>()

    //Create a read/write value for each string in document/new book data for the onClick listener
    private val _docTitle = MutableLiveData<String?>()
    private val _author = MutableLiveData<List<String>?>()
    private val _publishDate = MutableLiveData<List<String>?>()
    private val _isbn = MutableLiveData<List<String>?>()
    private val _subject = MutableLiveData<List<String>?>()

    //This is the read-only version for each string in document/new book data
    val docTitle: LiveData<String?>
        get() = _docTitle

    val author: LiveData<List<String>?>
        get() = _author
    val publishDate: LiveData<List<String>?>
        get() = _publishDate
    val isbn: LiveData<List<String>?>
        get() = _isbn
    val subject: LiveData<List<String>?>
        get() = _subject

    //Public read only status variable
    val status : LiveData<String>
        get() = _status



    //val _docs : MutableLiveData<List<Doc>> = MutableLiveData<List<Doc>>()
    /*  val _docs = MutableLiveData<List<Doc>>().also {
          viewModelScope.launch {
              try {
                  //"search.json?author=satter&fields=title%20author_name%20subject%20publish_date%20isbn"
                  /*val listResult = OpenLibraryApi.retrofitService.getBooks("search.json?author=satter")
                   _status.value = "Success: ${listResult.body()?.docs?.get(0)} Books Retrieved"*/
                  val listResult = OpenLibraryApi.retrofitService.getBooks(title = "$searchTerm")
                  it.value = listResult.body()?.docs
                  _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
              } catch (e: Exception) {
                  _status.value = "Failure: ${e.message}"
              }
          }
      }*/

    fun test(query:String?,categoryId : Int?) : MutableLiveData<List<Doc>>{
        val listo: MutableLiveData<List<Doc>> = MutableLiveData<List<Doc>>()
        runBlocking {
            val listResult: Response<NewBooks>
            when (searchCategory.value) {
                "title" -> {
                    Log.v("Error","$query $categoryId")
                    listResult = OpenLibraryApi.retrofitService.getBooks(title = query)
                    listo.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
                }

                "author" -> {
                    Log.v("Error","$query $categoryId")
                    listResult = OpenLibraryApi.retrofitService.getBooks(author = query)
                    listo.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
                }

                "subject" -> {
                    Log.v("Error","$query $categoryId")
                    listResult = OpenLibraryApi.retrofitService.getBooks(subject = query)
                    listo.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
                }
            }
        }
        return listo
    }

    val _docs = MutableLiveData<List<Doc>>().also { runBlocking {
        try {
            val listResult: Response<NewBooks>
            //set the search term to the correct category, obtained from the user selected chip
            when(category.value){
                2131231213 -> {listResult = OpenLibraryApi.retrofitService.getBooks(title = "${searchTerm.value}")
                    it.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"}

                2131230816 -> {listResult = OpenLibraryApi.retrofitService.getBooks(author = "${searchTerm.value}")
                    it.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"}

                2131231171 -> {listResult = OpenLibraryApi.retrofitService.getBooks(subject = "${searchTerm.value}")
                    it.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"}
            }

        } catch (e: Exception) {
            _status.value = "Failure: ${e.message}"
        }
    }
    }

    val docs : LiveData<List<Doc>>
        get() = _docs


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


    //Set _searchTerm value
    fun setSearchParams(term : String,category: Int,subject:Int?,title:Int?,author:Int?){
        _searchTerm.value = term
        _category.value = category

        when(category){
            subject -> _searchCategory.value = "subject"
            title -> _searchCategory.value = "title"
            author -> _searchCategory.value = "author"
        }

    }

    //When a book is clicked, send the new book (doc) info to the book details fragment
    fun docClicked(title: String, author:List<String>?,publishDate:List<String>?, isbn:List<String>?,subject:List<String>?){
        _docTitle.value = title
        _author.value = author
        _publishDate.value = publishDate
        _isbn.value = isbn
        _subject.value = subject
    }

    fun docNavigationComplete(){
        _docTitle.value = null
        _author.value = null
        _publishDate.value = null
        _isbn.value = null
        _subject.value = null
    }


}