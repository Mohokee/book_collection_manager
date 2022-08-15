package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hfad.bookcollectionmanager.data.BookDatabase
import com.hfad.bookcollectionmanager.databinding.FragmentEditBookBinding
import com.hfad.bookcollectionmanager.viewmodels.EditBookViewModel
import com.hfad.bookcollectionmanager.viewmodels.EditBookViewModelFactory

class EditBookFragment : Fragment() {
    private var _binding : FragmentEditBookBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBookBinding.inflate(inflater,container,false)
        val view = binding.root

        val bookId = EditBookFragmentArgs.fromBundle(requireArguments()).bookId

        val application = requireNotNull(this.activity).application
        val dao = BookDatabase.getInstance(application).bookDao

        val viewModelFactory = EditBookViewModelFactory(bookId,dao)
        val viewModel = ViewModelProvider(this,viewModelFactory)[EditBookViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                viewModel.resetStatus()

                //Toast
                Toast.makeText(context,"Changes Saved", Toast.LENGTH_LONG).show()

                //Navigate back to book details page
                view.findNavController()
                    .navigate(R.id.action_editBookFragment_to_bookCollectionFragment)

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