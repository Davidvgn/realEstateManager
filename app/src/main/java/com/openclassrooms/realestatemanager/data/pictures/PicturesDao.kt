package com.openclassrooms.realestatemanager.data.pictures

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PicturesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( picturesEntity: PicturesEntity)

    @Query("SELECT * FROM pictures")//todo david selectionner des photos en fonction de l'annonce
    fun getPicturesAsFlow(): Flow<List<PicturesEntity>>

    @Query("SELECT * FROM pictures")
    fun getAllPicturesWithCursor(): Cursor

}