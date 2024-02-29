package com.openclassrooms.realestatemanager.domain.pictures

import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

    fun getPicturesAsFlow(id: Long): Flow<List<PicturesEntity>>

    suspend fun addPicture(picturesEntity: PicturesEntity)
}