package com.openclassrooms.realestatemanager.data.pictures

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PicturesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(picturesEntity: PicturesEntity)

    @Query("SELECT * FROM pictures")
    fun getPicturesAsFlow(): Flow<List<PicturesEntity>>

//    @Query("SELECT * FROM pictures WHERE realEstateId =:id")
//    fun getPicturesAsFlow(id: Long): Flow<List<PicturesEntity>>

    @Query("SELECT * FROM pictures")
    fun getAllPicturesWithCursor(): Cursor

}