package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hfad.bookcollectionmanager.adapters.BookAdapter
import com.hfad.bookcollectionmanager.data.BookDatabase
import com.hfad.bookcollectionmanager.databinding.FragmentBookCollectionBinding
import com.hfad.bookcollectionmanager.utilities.onQueryTextChanged
import com.hfad.bookcollectionmanager.viewmodels.BookCollectionViewModel
import com.hfad.bookcollectionmanager.viewmodels.BookCollectionViewModelFactory

/*
* BookCollectionFragment hosts a recyclerview to display the list of books from Room
*/

class BookCollectionFragment : Fragment() {

    //Create data binding variables
    private var _binding: FragmentBookCollectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel : BookCollectionViewModel by viewModels()

    private lateinit var searchView: SearchView

    //This fragment will be editing the activity's toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Create the view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set data binding variables
        _binding = FragmentBookCollectionBinding.inflate(inflater,container,false)
        val view = binding.root

        //Set Application and Data Access Object values
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).bookDao

        val viewModelFactory = BookCollectionViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[BookCollectionViewModel::class.java]

        //Gives binding access to viewModel
        binding.viewModel = viewModel
        //Allows observation of livedata
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = BookAdapter {bookId ->
            viewModel.bookClicked(bookId)
        }
        binding.bookListView.adapter = adapter


        //pass data to adapter
        viewModel.books.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        //Navigate to the clicked-on book when its id isn't null
        viewModel.navigateToBook.observe(viewLifecycleOwner, Observer { bookId ->
            bookId?.let {
                val action = BookCollectionFragmentDirections
                    .actionBookCollectionFragmentToBookDetailsFragment(bookId)
                this.findNavController().navigate(action)
                viewModel.bookNavigatedTo()
            }
        })

        // FAB navigate to Add Book
        binding.fab.setOnClickListener {
            val action = BookCollectionFragmentDirections
                .actionBookFragmentToAddBookFragment()
            this.findNavController().navigate(action)
        }


            // Inflate the layout for this fragment
        return view
    }


    //Inflate the menu, and add search functionality
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //get the search view item and set it as a Search View
        val searchBook = menu.findItem(R.id.action_search)

        searchView = searchBook.actionView as SearchView

        //Restore the search query if the screen is rotated
        val pendingQuery = viewModel.searchQuery.value

        //If there was a search query in the bar on fragment rotation destruction,
        //expand the search view and set the query to pendingQuery
        if(pendingQuery != null && pendingQuery.isNotEmpty()){
            searchBook.expandActionView()
            searchView.setQuery(pendingQuery,false)
        }


        //Call SearchExt for SearchView to filter search
        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }

    //Hide the Book Shelf option on the activity's toolbar
    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.bookCollectionFragment)
        item.isVisible = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        searchView.setOnQueryTextListener(null)
    }

}