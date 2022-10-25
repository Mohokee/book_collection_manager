package com.hfad.bookcollectionmanager.utilities

import androidx.recyclerview.widget.DiffUtil
import com.hfad.bookcollectionmanager.data.Doc

/**A utility to find the differences between two lists of Docs, makes loading
 *in the recycler view more efficient
 **/
class DocDiffUtil : DiffUtil.ItemCallback<Doc>() {
    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean =
        (oldItem.title == newItem.title)

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean =
        (oldItem == newItem)
}
