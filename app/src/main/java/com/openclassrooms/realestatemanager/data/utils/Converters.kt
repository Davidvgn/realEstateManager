package com.openclassrooms.realestatemanager.data.utils

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        return string.split(",")
    }

    @TypeConverter
    fun fromLatLng(latLng: LatLng?): String? {
        return latLng?.let { "${it.latitude},${it.longitude}" }
    }

    @TypeConverter
    fun toLatLng(latLngString: String?): LatLng? {
        return latLngString?.split(",")?.let { parts ->
            if (parts.size == 2) {
                val latitude = parts[0].toDoubleOrNull()
                val longitude = parts[1].toDoubleOrNull()
                if (latitude != null && longitude != null) {
                    LatLng(latitude, longitude)
                } else {
                    null // Retourne null si l'une des valeurs de latitude ou longitude est null
                }
            } else {
                null // Retourne null si la chaîne n'est pas au format attendu
            }
        }
    }
}