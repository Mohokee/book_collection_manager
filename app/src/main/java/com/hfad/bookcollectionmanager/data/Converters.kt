package com.hfad.bookcollectionmanager.data

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    public fun fromList(list : List<String>) : String {
        var string : String = ""
        for(s in list){
            string += ("$s,")
        }
        return string
    }

    @TypeConverter
    public fun toList(concatString : String) : List<String>{
        val list : List<String> = listOf()

        for(s in concatString.split(",")){
            list +s
        }
        return list
    }
}