package com.openclassrooms.realestatemanager.domain.details

import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import kotlinx.coroutines.flow.Flow

interface DetailsRepository  {

    fun getRealEstatesByIdAsFlow(id: Long): Flow<RealEstateEntity>

}