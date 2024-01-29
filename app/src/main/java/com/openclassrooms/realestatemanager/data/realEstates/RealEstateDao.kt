package com.openclassrooms.realestatemanager.data.realEstates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Insert
    suspend fun insert(realEstateEntity: RealEstateEntity)

    @Query("SELECT * FROM realEstate")
    fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>>
}