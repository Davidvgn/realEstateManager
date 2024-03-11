package com.openclassrooms.realestatemanager.domain.pictures

import com.openclassrooms.realestatemanager.domain.pictures.model.DraftPictureEntity
import com.openclassrooms.realestatemanager.domain.pictures.model.PicturesEntity
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {
    fun getTemporaryPicturesAsFlow(): Flow<List<DraftPictureEntity>>

    fun getPicturesAsFlow(realEstateId: Long): Flow<List<PicturesEntity>>

    suspend fun addPicture(picturesEntity: PicturesEntity)

    suspend fun addTemporaryPicturesList(draftPictureEntity: DraftPictureEntity)

    suspend fun deleteTemporaryPicture(draftPictureEntity: DraftPictureEntity)

    suspend fun deleteTemporaryPicturesList()
}
