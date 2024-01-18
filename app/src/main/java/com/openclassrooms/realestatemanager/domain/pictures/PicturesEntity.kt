package com.openclassrooms.realestatemanager.domain.pictures

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.domain.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.UserEntity

@Entity(tableName = "pictures",
    foreignKeys = [ForeignKey(
        entity = RealEstateEntity::class,
        parentColumns = ["id"],
        childColumns = ["realEstateId"],
    )])
data class PicturesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val realEstateId: Int,
    val image: String,
    val description: String
)