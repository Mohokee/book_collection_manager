package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hfad.bookcollectionmanager.data.BookDatabase
import com.hfad.bookcollectionmanager.databinding.FragmentAddBookBinding
import com.hfad.bookcollectionmanager.viewmodels.AddBookViewModel
import com.hfad.bookcollectionmanager.viewmodels.AddBookViewModelFactory


class AddBookFragment : Fragment() {
    private var _binding : FragmentAddBookBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBookBinding.inflate(inflater,container,false)
        val view = binding.root

        //Build database if nonexistent, get BookDao reference
        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).bookDao

        //Get ViewModel
        val viewModelFactory = AddBookViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(AddBookViewModel::class.java)

        //Set data binding
        binding.viewModel = viewModel

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                //set to null so it won't be triggered again
                viewModel.status.value = null
                Toast.makeText(context,"Book Added",Toast.LENGTH_LONG).show()
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