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
class DocAdapter(val clickListener: (title:String?,author:List<String>?,publishDate:List<String>?,isbn:List<String>?,subject:List<String>?) -> Unit) : ListAdapter<Doc, DocAdapter.DocViewHolder>(DocDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : DocViewHolder = DocViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: DocViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }
    class DocViewHolder(val binding: DocBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): DocViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DocBinding.inflate(layoutInflater, parent, false)
                return DocViewHolder(binding)
            }
        }
        fun bind(item: Doc, clickListener: (title: String?,author:List<String>?, publishDate:List<String>?,isbn:List<String>?,subject:List<String>?) -> Unit) {
            binding.doc = item
            binding.cover.load(item.cover){placeholder(R.drawable.no_cover)}

            //if there is no cover returned from the api, set the cover to the placeholder image,
            //this doesn't work for blank .jpgs
            if(item.cover.contains("null")){
                binding.cover.setImageResource(R.drawable.no_cover)
            }

            binding.root.setOnClickListener {
                clickListener(item.title,
                    item.authorName,item.publishDate,item.isbn,item.subject)
            }

        }
    }
}