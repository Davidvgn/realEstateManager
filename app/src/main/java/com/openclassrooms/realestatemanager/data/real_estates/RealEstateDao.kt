package com.openclassrooms.realestatemanager.data.real_estates

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Insert
    suspend fun insert(realEstateEntity: RealEstateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRealEstate(realEstate: RealEstateEntity): Long


    @Query("SELECT * FROM realEstate")
    fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>>

    @Query("SELECT * FROM realEstate WHERE id = :realEstateId")
    fun getRealEstateByIdAsFlow(realEstateId: Long): Flow<RealEstateEntity>

    @Query("SELECT * FROM realEstate")
    fun getAllPropertiesWithCursor(): Cursor

    @Query("DELETE FROM realEstate WHERE id=:realEstateId")
    suspend fun delete(realEstateId: Long): Int

}