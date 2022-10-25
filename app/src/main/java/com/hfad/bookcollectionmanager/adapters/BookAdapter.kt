package com.hfad.bookcollectionmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfad.bookcollectionmanager.data.Book
import com.hfad.bookcollectionmanager.databinding.BookBinding
import com.hfad.bookcollectionmanager.utilities.BookDiffUtil

/**
 * The Book Collection Fragment recycler view adapter.
 * It uses DiffUtil to compare the fragment list from the room list, then only loads new ones to the fragment list.
 * It also passes in a click listener to get data by it's book id when a book is clicked
 */

class BookAdapter(val clickListener: (bookId: Long) -> Unit) :
    ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffUtil()) {


    /**
     * Creates the viewholder used by this adapter
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder.inflateFrom(parent)


    /**
     * Passes bind method to individual items and adds a click listener
     */
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book, clickListener)
    }

    /**
     * Defines View Holder
     */
    class BookViewHolder(val binding: BookBinding) : RecyclerView.ViewHolder(binding.root) {
        //Create each view holder, inflate its layout
        companion object {
            fun inflateFrom(parent: ViewGroup): BookViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BookBinding.inflate(layoutInflater, parent, false)
                return BookViewHolder(binding)
            }
        }
        /**
         * Binds each book from Room database
         */

        fun bind(book: Book, clickListener: (bookId: Long) -> Unit) {
            binding.book = book
            binding.root.setOnClickListener {
                clickListener(book.BookId)
            }
        }
    }
}