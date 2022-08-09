package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hfad.bookcollectionmanager.adapters.BookAdapter
import com.hfad.bookcollectionmanager.data.BookDatabase
import com.hfad.bookcollectionmanager.databinding.FragmentBookCollectionBinding
import com.hfad.bookcollectionmanager.viewmodels.BookCollectionViewModel
import com.hfad.bookcollectionmanager.viewmodels.BookCollectionViewModelFactory

/*
* BookCollectionFragment hosts a recyclerview to display the list of books from Room
*/

class BookCollectionFragment : Fragment() {

    //Create data binding variables
    private var _binding: FragmentBookCollectionBinding? = null
    private val binding get() = _binding!!

    //Create the view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set data binding variables
        _binding = FragmentBookCollectionBinding.inflate(inflater,container,false)
        val view = binding.root

        //Set Application and Data Access Object variables
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).bookDao

        val viewModelFactory = BookCollectionViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[BookCollectionViewModel::class.java]

        binding.viewModel = viewModel
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



        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}