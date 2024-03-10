package com.openclassrooms.realestatemanager.domain.real_estates

import kotlinx.coroutines.flow.Flow

interface RealEstatesRepository {
    suspend fun add(realEstate: RealEstateEntity): Long

    fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>>

    fun isListEmptyAsFlow(): Flow<Boolean>

    suspend fun delete(realEstateId: Long): Boolean
}
