package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.data.dao.RealEstateDao
import com.openclassrooms.realestatemanager.domain.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepositoryImpl @Inject constructor(
    private val realEstateDao: RealEstateDao,
    ): RealEstatesRepository {
    override fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>> =
        realEstateDao.getRealEstatesAsFlow().flowOn(Dispatchers.IO)

}