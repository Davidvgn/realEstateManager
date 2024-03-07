package com.openclassrooms.realestatemanager.domain.real_estates

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "realEstate")
data class RealEstateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val creationDate: String,
    val type: String?,
    val photo: String?,
    var salePrice: String?,
    val floorArea: String?,
    val numberOfRooms: String?,
    val description: String?,
    val pointOfInterest: List<String?>,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: String?,
    val latLng: LatLng?,
)
