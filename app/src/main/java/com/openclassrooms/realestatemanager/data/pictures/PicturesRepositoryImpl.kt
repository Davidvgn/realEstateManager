package com.openclassrooms.realestatemanager.data.pictures

import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.pictures.PicturesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepositoryImpl @Inject constructor(
    private val picturesDao: PicturesDao
): PicturesRepository {

    override fun getPicturesAsFlow(): Flow<List<PicturesEntity>> =
        picturesDao.getPicturesAsFlow().flowOn(Dispatchers.IO)

    override suspend fun addPictures(pictures: List<PicturesEntity>) {
        withContext(Dispatchers.IO) {
            picturesDao.insertAll(pictures)
        }
    }
}