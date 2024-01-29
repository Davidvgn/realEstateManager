package com.openclassrooms.realestatemanager.domain.real_estates

import kotlinx.coroutines.flow.Flow

interface RealEstatesRepository {

    suspend fun add(realEstateEntity: RealEstateEntity)
    fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>>
}