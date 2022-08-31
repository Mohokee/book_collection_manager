package com.hfad.bookcollectionmanager.utilities

import androidx.recyclerview.widget.DiffUtil
import com.hfad.bookcollectionmanager.data.Doc

class DocDiffUtil : DiffUtil.ItemCallback<Doc>() {
    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean =
        (oldItem.title == newItem.title)

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean =
        (oldItem == newItem)
}
