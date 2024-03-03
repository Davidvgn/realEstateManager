package com.openclassrooms.realestatemanager.data.pictures

import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.pictures.PicturesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepositoryImpl @Inject constructor(
    private val picturesDao: PicturesDao
) : PicturesRepository {


    private val temporaryPicturesList = mutableListOf<PicturesEntity>()
    private val temporaryPicturesFlow = MutableStateFlow<List<PicturesEntity>>(emptyList())

    override fun getPicturesAsFlow(): Flow<List<PicturesEntity>> {
        return picturesDao.getPicturesAsFlow()
    }

    override fun getPicturesNoDAO(): Flow<List<PicturesEntity>> {
        return temporaryPicturesFlow
    }

//    override fun getPicturesAsFlow(id: Long): Flow<List<PicturesEntity>> {
//        return picturesDao.getPicturesAsFlow(id)
//    }


    override suspend fun addPicture(picturesEntity: PicturesEntity) {
        picturesDao.insert(picturesEntity)
    }

    override suspend fun addTemporaryPicturesList(picturesEntity: PicturesEntity) {
        temporaryPicturesList.add(picturesEntity)
        temporaryPicturesFlow.value = temporaryPicturesList.toList()
    }

    override suspend fun deleteTemporaryPicturesList() {
        temporaryPicturesList.clear()
        temporaryPicturesFlow.value = temporaryPicturesList.toList()
    }
}