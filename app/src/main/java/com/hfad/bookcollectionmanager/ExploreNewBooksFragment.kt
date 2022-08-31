package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        //set layout manager for recycler view
        binding.newBooksView.layoutManager= LinearLayoutManager(requireContext())

        val adapter = DocAdapter()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}