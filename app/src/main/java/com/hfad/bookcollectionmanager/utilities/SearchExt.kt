package com.hfad.bookcollectionmanager.utilities

/**
 * Import the SearchView class that needs an added function
*/
import androidx.appcompat.widget.SearchView


/**
 * Search function for the main book collection
 */
inline fun SearchView.onQueryTextChanged(crossinline listener: (String) ->  Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}