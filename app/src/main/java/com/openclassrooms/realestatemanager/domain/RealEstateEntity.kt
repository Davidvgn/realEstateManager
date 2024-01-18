package com.openclassrooms.realestatemanager.domain

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "realEstate",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["realEstateAgent"],
    )]
)
data class RealEstateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String?,
    val salePrice: Int?,
    val floorArea: Int?,
    val numberOfRooms: Int?,
    val description: String?,
    val Photo: String?,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: Int?
)
