package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.bookcollectionmanager.adapters.DocAdapter
import com.hfad.bookcollectionmanager.databinding.FragmentExploreNewBooksBinding
import com.hfad.bookcollectionmanager.viewmodels.ExploreNewBooksViewModel

/**
 * This fragment displays a recyclerView with data from the OpenLibrary api
 */
class ExploreNewBooksFragment : Fragment() {
    private var _binding: FragmentExploreNewBooksBinding? = null
    private val binding get() = _binding!!

    /**
     * This fragment will be editing the activity's toolbar
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Set custom options menu
         */
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * Set Binding var
         * */
        _binding = FragmentExploreNewBooksBinding.inflate(inflater, container, false)
        val view = binding.root


        /**
         * View Model
         * */
        val viewModel: ExploreNewBooksViewModel by viewModels()

        /**
         *  Gives binding access to viewModel
         */
        binding.viewModel = viewModel

        /**
         * Allows observation of livedata
         */
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * Livedata vals for new Document elements
         * */
        val docTitle = viewModel.docTitle
        val docAuthor = viewModel.author
        val docPublish = viewModel.publishDate
        val docIsbn = viewModel.isbn
        val docSubject = viewModel.subject

        /**
         * set layout manager for recycler view
         */
        binding.newBooksView.layoutManager = LinearLayoutManager(requireContext())

        /**
         * Set the recyclerView adapter
         * */
        val adapter = DocAdapter { title, author, publishDate, isbn, subject ->
            viewModel.docClicked(
                title ?: "Untitled",
                author ?: listOf("Unknown"),
                publishDate ?: listOf("None"),
                isbn ?: listOf("None"),
                subject ?: listOf("None")
            )
            /**
             * Navigate to new book details on click, and send new book details in bundle as Strings
             */
            val action = ExploreNewBooksFragmentDirections
                .actionExploreNewBooksFragmentToNewBookDetailsFragment(
                    docTitle.value.toString(),
                    docAuthor.value?.joinToString(",") ?: "None",
                    docPublish.value?.joinToString(",") ?: "None",
                    docIsbn.value?.joinToString(",") ?: "None",
                    docSubject.value?.joinToString(",") ?: "None"
                )
            /**
             * Navigate to new book details page
             */
            this.findNavController().navigate(action)
            /**
             * Set values to null once the page has navigated
             */
            viewModel.docNavigationComplete()
        }

        /**
         * Send search query to API
         */
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val subject = binding.subject.id
                    val title = binding.title.id
                    val author = binding.author.id
                    viewModel.setSearchParams(
                        query,
                        binding.searchChipGroup.checkedChipId,
                        subject,
                        title,
                        author
                    )

                    /**
                     * Submit the list the query returned to the recycler view adapter
                     */
                    adapter.submitList(
                        viewModel.submitSearch(
                            viewModel.searchTerm.value,
                            viewModel.category.value
                        ).value
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        /**
         * Set adapter
         */
        binding.newBooksView.adapter = adapter

        /**
         * Inflate the layout for this fragment
         */

        return view
    }

    /**
     * Hide the search icon on the activity's toolbar
     */

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_search)
        item.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}