package com.openclassrooms.realestatemanager.domain.pictures

import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

//    fun getPicturesAsFlow(): Flow<List<PicturesEntity>>
    fun getPicturesNoDAO(): Flow<List<PicturesEntity>>
    fun getPicturesAsFlow(realEstateId: Long): Flow<List<PicturesEntity>>


    suspend fun addPicture(picturesEntity: PicturesEntity)

    suspend fun addTemporaryPicturesList(picturesEntity: PicturesEntity)
    suspend fun deleteTemporaryPicturesList()
}