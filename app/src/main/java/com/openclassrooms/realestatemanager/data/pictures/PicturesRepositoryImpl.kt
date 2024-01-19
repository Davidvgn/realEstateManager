package com.openclassrooms.realestatemanager.data.pictures

import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.pictures.PicturesRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepositoryImpl @Inject constructor(
    private val picturesDao: PicturesDao
): PicturesRespository {
    override fun getPicturesAsFlow(): Flow<List<PicturesEntity>> =
        picturesDao.getPicturesAsFlow().flowOn(Dispatchers.IO)

}