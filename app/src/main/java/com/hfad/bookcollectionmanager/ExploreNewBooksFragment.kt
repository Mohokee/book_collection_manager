package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

    //This fragment will be editing the activity's toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set Binding var
        _binding = FragmentExploreNewBooksBinding.inflate(inflater, container, false)
        val view = binding.root

        //Set Application value
        val application = requireNotNull(this.activity).application

        //View Model
        val viewModel: ExploreNewBooksViewModel by viewModels()

        //Gives binding access to viewModel
        binding.viewModel = viewModel

        //Allows observation of livedata
        binding.lifecycleOwner = viewLifecycleOwner

        //Livedata vals for new Document elements
        val docTitle = viewModel.docTitle
        val docAuthor = viewModel.author
        val docPublish = viewModel.publishDate
        val docIsbn = viewModel.isbn
        val docSubject = viewModel.subject

        //set layout manager for recycler view
        binding.newBooksView.layoutManager = LinearLayoutManager(requireContext())

        //Set the recyclerView adapter
        val adapter = DocAdapter { title, author, publishDate, isbn, subject ->
            viewModel.docClicked(
                title ?: "Untitled",
                author ?: listOf("Unknown"),
                publishDate ?: listOf("None"),
                isbn ?: listOf("None"),
                subject ?: listOf("None")
            )
            val action = ExploreNewBooksFragmentDirections
                .actionExploreNewBooksFragmentToNewBookDetailsFragment(
                    docTitle.value.toString(),
                    docAuthor.value.toString(),
                    docPublish.value.toString(),
                    docIsbn.value.toString(),
                    docSubject.value.toString()
                )
            this.findNavController().navigate(action)
            viewModel.docNavigationComplete()
            Log.v("VERBOSE", "${docAuthor.value}}")
        }

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

                    // 10/2/2022 - trying to get id from each chip from text id so it doesn't matter if it gets
                    // changed when re-downloaded
                    Log.v("VERBOSE", "${binding.subject.id}")
                    //Should match this one for subject
                    Log.v("VERBOSE", "$query ${binding.searchChipGroup.checkedChipId}")

                    Log.v("VERBOSE", "{${viewModel.searchTerm.value} ${viewModel.category.value}}")
                    Log.v(
                        "VERBOSE",
                        viewModel.test(
                            viewModel.searchTerm.value,
                            viewModel.category.value
                        ).value.toString()
                    )
                    adapter.submitList(
                        viewModel.test(
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


        //Set observer for openlibrary api livedata, send it to the recycler view's adapter
        viewModel.docs.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.test.text = it.size.toString()
                adapter.submitList(it)
            }
        })

        binding.newBooksView.adapter = adapter

        // Inflate the layout for this fragment
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