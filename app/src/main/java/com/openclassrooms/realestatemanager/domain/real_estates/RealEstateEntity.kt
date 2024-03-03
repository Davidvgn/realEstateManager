package com.openclassrooms.realestatemanager.domain.real_estates

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "realEstate")
data class RealEstateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String?,
    val photo: String?,
    val salePrice: String?,
    val floorArea: String?,
    val numberOfRooms: Int?,
    val description: String?,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: Int?
)
