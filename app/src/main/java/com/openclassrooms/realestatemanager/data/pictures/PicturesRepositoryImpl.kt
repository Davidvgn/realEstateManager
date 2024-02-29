package com.openclassrooms.realestatemanager.data.pictures

import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.pictures.PicturesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepositoryImpl @Inject constructor(
    private val picturesDao: PicturesDao
): PicturesRepository {


    override fun getPicturesAsFlow(id: Long): Flow<List<PicturesEntity>> {
       return picturesDao.getPicturesAsFlow(id)
    }

    override suspend fun addPicture(picturesEntity: PicturesEntity) {
        picturesDao.insert(picturesEntity)
    }
}