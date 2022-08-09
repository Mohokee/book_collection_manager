package com.hfad.bookcollectionmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfad.bookcollectionmanager.data.Book
import com.hfad.bookcollectionmanager.databinding.BookBinding
import com.hfad.bookcollectionmanager.utilities.BookDiffUtil

/*
* The Book Collection Fragment recycler view adapter. It uses DiffUtil to make loading the
list of books from Room more efficient
* */

class BookAdapter(val clickListener: (bookId : Long) -> Unit) : ListAdapter<Book,BookAdapter.BookViewHolder>(BookDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder = BookViewHolder.inflateFrom(parent)


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book,clickListener)
    }

    class BookViewHolder(val binding : BookBinding) : RecyclerView.ViewHolder(binding.root){
        companion object{
            fun inflateFrom(parent: ViewGroup):BookViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BookBinding.inflate(layoutInflater,parent,false)
                return BookViewHolder(binding)
            }
        }

        fun bind(book:Book, clickListener: (bookId: Long) -> Unit){
            binding.book = book
            binding.root.setOnClickListener {
                clickListener(book.BookId)
            }
        }
    }
}