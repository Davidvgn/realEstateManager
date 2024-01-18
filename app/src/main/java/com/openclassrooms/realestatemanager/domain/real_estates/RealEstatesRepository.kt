package com.openclassrooms.realestatemanager.domain.real_estates

import com.openclassrooms.realestatemanager.domain.RealEstateEntity
import kotlinx.coroutines.flow.Flow

interface RealEstatesRepository {
    fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>>
}