package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PicturesDao {
    @Insert
    suspend fun insert( picturesEntity: PicturesEntity)

    @Query("SELECT * FROM pictures")//todo david selectionner des photos en fonction de l'annonce
    fun getPicturesAsFlow(): Flow<List<PicturesEntity>>
}