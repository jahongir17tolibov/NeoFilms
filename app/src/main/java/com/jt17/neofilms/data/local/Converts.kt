package com.jt17.neofilms.data.local

import androidx.room.TypeConverter

/**
 * converts List to and from String
 */

class Converts {

    @TypeConverter
    fun listToString(values: List<Int>): String {
        val strList = mutableListOf<String>()
        values.map {
            strList.add(it.toString())
        }
        return strList.joinToString(",")
    }

    @TypeConverter
    fun stringToList(value: String): List<Int> {
        val intList = mutableListOf<Int>()
        value.split(",").map {
            intList.add(it.toInt())
        }
        return intList
    }

}