package com.openclassrooms.realestatemanager.domain.details

import com.openclassrooms.realestatemanager.domain.real_estates.model.RealEstateEntity
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getRealEstatesByIdAsFlow(id: Long): Flow<RealEstateEntity>

    fun getPoiListByRealEstateId(realEstateId: Long): Flow<List<String>>
}
