package com.openclassrooms.realestatemanager.domain.pictures.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.domain.real_estates.model.RealEstateEntity

@Entity(
    tableName = "pictures",
    foreignKeys = [
        ForeignKey(
            entity = RealEstateEntity::class,
            parentColumns = ["id"],
            childColumns = ["realEstateId"],
        ),
    ],
)
data class PicturesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val realEstateId: Long?,
    val uri: String,
    val title: String,
)
