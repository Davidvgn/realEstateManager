package com.openclassrooms.realestatemanager.data.real_estates

import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepositoryRoom
    @Inject
    constructor(
        private val realEstateDao: RealEstateDao,
    ) : RealEstatesRepository {
        override suspend fun add(realEstate: RealEstateEntity) = realEstateDao.insertRealEstate(realEstate)

        override fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>> = realEstateDao.getRealEstatesAsFlow().flowOn(Dispatchers.IO)

        override fun isListEmptyAsFlow(): Flow<Boolean> = realEstateDao.getRealEstatesAsFlow().map { list -> list.isEmpty() }

        override suspend fun delete(realEstateId: Long): Boolean =
            withContext(Dispatchers.IO) {
                realEstateDao.delete(realEstateId) == 1
            }
    }
