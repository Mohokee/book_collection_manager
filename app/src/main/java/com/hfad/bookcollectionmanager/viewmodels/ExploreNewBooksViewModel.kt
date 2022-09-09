package com.hfad.bookcollectionmanager.viewmodels

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

    private  var docMutableList: MutableLiveData<List<Doc>> = MutableLiveData()

    fun test(query :String, categoryId : Int) : MutableLiveData<List<Doc>>{
        val listo: MutableLiveData<List<Doc>> = MutableLiveData<List<Doc>>()
        runBlocking {
            val listResult: Response<NewBooks>
            when (categoryId) {
                2131231213 -> {
                    listResult = OpenLibraryApi.retrofitService.getBooks(title = query)
                    listo.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
                }

                2131230816 -> {
                    listResult = OpenLibraryApi.retrofitService.getBooks(author = query)
                    listo.value = listResult.body()?.docs
                    _status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
                }

                2131231171 -> {
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

    fun getBooksFromTest(query :String){
        viewModelScope.launch{
            try {
                //"search.json?author=satter&fields=title%20author_name%20subject%20publish_date%20isbn"
                /*val listResult = OpenLibraryApi.retrofitService.getBooks("search.json?author=satter")
                 _status.value = "Success: ${listResult.body()?.docs?.get(0)} Books Retrieved"*/
                val listResult = OpenLibraryApi.retrofitService.getBooks(title = query)
                //_status.value = "Success: ${listResult.body()?.docs} Books Retrieved"
                _docs.postValue(listResult.body()!!.docs)
                docMutableList.value = listResult.body()?.docs

            } catch(e:Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    //Set _searchTerm value
    fun setSearchParams(term : String,category: Int){
        _searchTerm.value = term
        _category.value = category
    }

    //When a book is clicked, send the book id to the book details fragment
    fun docClicked(title: String){
        _navigateToDoc.value = title
    }


}