package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.bookcollectionmanager.data.BookDatabase
import com.hfad.bookcollectionmanager.databinding.FragmentNewBookDetailsBinding
import com.hfad.bookcollectionmanager.viewmodels.NewBookDetailsViewModel
import com.hfad.bookcollectionmanager.viewmodels.NewBookDetailsViewModelFactory

class NewBookDetailsFragment : Fragment() {
    private var _binding: FragmentNewBookDetailsBinding? = null
    private val binding get() = _binding!!

    //This fragment will be editing the activity's toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBookDetailsBinding.inflate(inflater,container,false)
        val view = binding.root

        //Build database if nonexistent, get BookDao reference
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).bookDao

        val title = NewBookDetailsFragmentArgs.fromBundle(requireArguments()).title
        val author = NewBookDetailsFragmentArgs.fromBundle(requireArguments()).author
        val publishDate = NewBookDetailsFragmentArgs.fromBundle(requireArguments()).publishDate
        val isbn = NewBookDetailsFragmentArgs.fromBundle(requireArguments()).isbn
        val subject = NewBookDetailsFragmentArgs.fromBundle(requireArguments()).subject

        //Get View Model
        val viewModelFactory = NewBookDetailsViewModelFactory(title,author,publishDate,isbn,subject,dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[NewBookDetailsViewModel::class.java]

        //Gives binding access to viewModel
        binding.viewModel = viewModel

        //Allows observation of livedata
        binding.lifecycleOwner = viewLifecycleOwner
        // Inflate the layout for this fragment

        //Set observer to Toast after book has been added to the database, then navigate to main collection
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                //Set to null so it won't be triggered again, using viewModel method
                viewModel.resetStatus()

                //Toast
                Toast.makeText(context,"Book Added", Toast.LENGTH_LONG).show()

                //Navigate back to book collection page
                view.findNavController()
                    .navigate(R.id.action_newBookDetailsFragment_to_bookCollectionFragment)

            }
        })

        return view

    }
    //Hide the search icon on the activity's toolbar
    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_search)
        item.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}