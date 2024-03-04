package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.data.real_estates.RealEstateDao
import com.openclassrooms.realestatemanager.domain.details.DetailsRepository
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val realEstateDao: RealEstateDao
) : DetailsRepository {

    override fun getRealEstatesByIdAsFlow(id: Long): Flow<RealEstateEntity> =
        realEstateDao.getRealEstateByIdAsFlow(id).flowOn(Dispatchers.IO)

}