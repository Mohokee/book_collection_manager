package com.hfad.bookcollectionmanager.data

import com.squareup.moshi.Json
/**Define Database name, column names, and data types
*Doc/New Book*/
data class Doc(
    @Json(name="author_name")
    val authorName: List<String>? = null,
    @Json(name="publish_date")
    val publishDate: List<String>? = null,
    @Json(name="subject")
    val subject: List<String>? = null,
    @Json(name="title")
    val title: String? = null,
    @Json(name="isbn")
    val isbn: List<String>? = null,
    val cover: String = "https://covers.openlibrary.org/b/isbn/${isbn?.get(0)}-M.jpg"
)