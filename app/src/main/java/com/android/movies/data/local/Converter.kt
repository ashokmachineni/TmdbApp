package com.android.movies.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromIntList(list: List<Int?>?): String = Gson().toJson(list)

        @TypeConverter
        @JvmStatic
        fun toIntList(value: String): List<Int?>? {
            val listType = object : TypeToken<List<Int?>?>() {}.type
            return Gson().fromJson(value, listType)
        }


        @TypeConverter
        @JvmStatic
        fun fromStringList(list: List<String?>?): String = Gson().toJson(list)

        @TypeConverter
        @JvmStatic
        fun toStringList(value: String): List<String?>? {
            val listType = object : TypeToken<List<String?>?>() {}.type
            return Gson().fromJson(value, listType)
        }

    }

}