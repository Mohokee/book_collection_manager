package com.hfad.bookcollectionmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hfad.bookcollectionmanager.R
import com.hfad.bookcollectionmanager.data.Doc
import com.hfad.bookcollectionmanager.databinding.DocBinding
import com.hfad.bookcollectionmanager.utilities.DocDiffUtil

/**
* The Explore new books recycler view adapter.
* It uses DiffUtil to compare the fragment list from the api list, then only loads new ones to the fragment list.
* It also passes in a click listener to get data by it's book id when a book is clicked
*/
class DocAdapter(val clickListener: (title: String?, author: List<String>?, publishDate: List<String>?, isbn: List<String>?, subject: List<String>?) -> Unit) :
    ListAdapter<Doc, DocAdapter.DocViewHolder>(DocDiffUtil()) {

    /**
    * Creates the viewholder used by this adapter
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : DocViewHolder = DocViewHolder.inflateFrom(parent)
    /**
    * Passes bind method to individual items and adds a click listener
    */
    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    /**
     * Defines View Holder
    */
    class DocViewHolder(val binding: DocBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
        * Create each view holder, inflate its layout
        */
        companion object {
            fun inflateFrom(parent: ViewGroup): DocViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DocBinding.inflate(layoutInflater, parent, false)
                return DocViewHolder(binding)
            }
        }

        /**
         * Binds each document(new book) to an item, loads the item's cover if applicable
         */
        fun bind(
            item: Doc,
            clickListener: (title: String?, author: List<String>?, publishDate: List<String>?, isbn: List<String>?, subject: List<String>?) -> Unit
        ) {
            binding.doc = item
            binding.cover.load(item.cover) { placeholder(R.drawable.no_cover) }

            /**If there is no cover returned from the api, set the cover to the placeholder image,
            *this doesn't work for blank .jpgs
             */
            if (item.cover.contains("null")) {
                binding.cover.setImageResource(R.drawable.no_cover)
            }

            /**
             * Set click listener to get item data to send to new book details fragment
             */
            binding.root.setOnClickListener {
                clickListener(
                    item.title,
                    item.authorName, item.publishDate, item.isbn, item.subject
                )
            }

        }
    }
}