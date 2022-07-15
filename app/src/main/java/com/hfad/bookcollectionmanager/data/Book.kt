package com.hfad.bookcollectionmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Define Database name, column names, and data types
@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var BookId : Long = 0L,

    @ColumnInfo(name = "title")
    var title:String = "",

    @ColumnInfo(name = "author")
    var author : String = "",

    @ColumnInfo(name = "tags")
    var tags: List<String> = listOf<String>(),

    @ColumnInfo(name = "description")
    var description: String = ""

)