package com.openclassrooms.realestatemanager.domain.real_estates

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.domain.UserEntity

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
    val id: Long = 0,
    val type: String?,
    val salePrice: String?,
    val floorArea: String?,
    val numberOfRooms: Int?,
    val description: String?,
    val photo: String?,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: Int?
)
