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
import com.hfad.bookcollectionmanager.databinding.FragmentAddBookBinding
import com.hfad.bookcollectionmanager.viewmodels.AddBookViewModel
import com.hfad.bookcollectionmanager.viewmodels.AddBookViewModelFactory


class AddBookFragment : Fragment() {
    /**
     * Binding backing variable, can be null
     */
    private var _binding : FragmentAddBookBinding? = null

    /**
     * This fragment will be editing the activity's toolbar
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Read only version of binding, cannot be null
     */
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBookBinding.inflate(inflater,container,false)
        val view = binding.root

        /**
         * Build database if nonexistent, get BookDao reference
         */
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).bookDao

        /**
         * Get ViewModel
         */
        val viewModelFactory = AddBookViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(AddBookViewModel::class.java)

        /**
         * Set data binding
         */
        binding.viewModel = viewModel

        /**
         * Set observer to Toast after book has been added to the database, then navigate to main collection
         * */
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                /**
                 * Set to null so it won't be triggered again, using viewModel method
                 * */
                viewModel.resetStatus()

                /**
                 * Toast
                 * */
                Toast.makeText(context,"Book Added",Toast.LENGTH_LONG).show()

                /**
                 * Navigate back to book collection page
                 * */
                view.findNavController()
                    .navigate(R.id.action_addBookFragment_to_bookCollectionFragment)

            }
        })

        /**
         * Inflate the layout for this fragment
         * */
        return view
    }

    /**
     * Hide the search icon on the activity's toolbar
     */
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