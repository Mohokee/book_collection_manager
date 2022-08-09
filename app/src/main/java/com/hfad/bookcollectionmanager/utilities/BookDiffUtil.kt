package com.hfad.bookcollectionmanager.utilities

import androidx.recyclerview.widget.DiffUtil
import com.hfad.bookcollectionmanager.data.Book

//A utility to find the differences between two lists, makes loading
//in the recycler view more efficient
class BookDiffUtil: DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        (oldItem.BookId == newItem.BookId)

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
        (oldItem == newItem)


}