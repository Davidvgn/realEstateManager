package com.openclassrooms.realestatemanager.data.realEstates

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
class RealEstateRepositoryRoom @Inject constructor(
    private val realEstateDao: RealEstateDao,
) : RealEstatesRepository {


    override suspend fun add(realEstateEntity: RealEstateEntity): Unit =
        withContext(Dispatchers.IO) {
            realEstateDao.insert(realEstateEntity)
        }

    override fun getRealEstatesAsFlow(): Flow<List<RealEstateEntity>> =
        realEstateDao.getRealEstatesAsFlow().flowOn(Dispatchers.IO)

    override fun isListEmptyAsFlow(): Flow<Boolean> =
        realEstateDao.getRealEstatesAsFlow().map { list -> list.isEmpty() }

}