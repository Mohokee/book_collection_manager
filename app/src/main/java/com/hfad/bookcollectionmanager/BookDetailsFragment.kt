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
import androidx.navigation.fragment.findNavController
import com.hfad.bookcollectionmanager.data.BookDatabase
import com.hfad.bookcollectionmanager.databinding.FragmentBookDetailsBinding
import com.hfad.bookcollectionmanager.viewmodels.BookDetailsViewModel
import com.hfad.bookcollectionmanager.viewmodels.BookDetailsViewModelFactory

class BookDetailsFragment : Fragment() {
    private var _binding : FragmentBookDetailsBinding? = null
    private val binding get() = _binding!!

    /**
     * This fragment will be editing the activity's toolbar
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * Inflate the layout for this fragment
         * */
        _binding = FragmentBookDetailsBinding.inflate(inflater,container,false)
        val view = binding.root

        /**
         * Get the book id of the book clicked on in the main collection from the bundle
         */
        val bookId = BookDetailsFragmentArgs.fromBundle(requireArguments()).bookId

        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).bookDao

        val viewModelFactory = BookDetailsViewModelFactory(bookId,dao)
        val viewModel = ViewModelProvider(this,viewModelFactory)[BookDetailsViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * Set editBook button to go to edit book fragment
         */
        binding.toEditBookButton.setOnClickListener {
            val action = BookDetailsFragmentDirections
                .actionBookDetailsFragmentToEditBookFragment(bookId)
            this.findNavController().navigate(action)
        }

        /**
         * Set status variable to tell when to toast and go back to the main page after book deletion
         */
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                viewModel.resetStatus()

                /**
                 * Toast
                 * */
                Toast.makeText(context,"Book Deleted", Toast.LENGTH_LONG).show()

                /**
                 *Back to book details page
                 */
                view.findNavController()
                    .navigate(R.id.action_bookDetailsFragment_to_bookCollectionFragment)

            }
        })

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