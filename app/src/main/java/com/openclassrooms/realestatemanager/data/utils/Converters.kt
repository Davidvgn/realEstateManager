package com.openclassrooms.realestatemanager.data.utils

import androidx.room.TypeConverter

class Converters {
        @TypeConverter
        fun fromStringList(list: List<String>): String {
            return list.joinToString(separator = ",")
        }

        @TypeConverter
        fun toStringList(string: String): List<String> {
            return string.split(",")
        }
}