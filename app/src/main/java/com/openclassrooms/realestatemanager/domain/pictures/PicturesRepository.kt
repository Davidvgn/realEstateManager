package com.openclassrooms.realestatemanager.domain.pictures

import com.openclassrooms.realestatemanager.domain.pictures.draft_picture.DraftPictureEntity
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {
    fun getTemporaryPicturesAsFlow(): Flow<List<DraftPictureEntity>>

    fun getPicturesAsFlow(realEstateId: Long): Flow<List<PicturesEntity>>

    suspend fun addPicture(picturesEntity: PicturesEntity)

    suspend fun addTemporaryPicturesList(draftPictureEntity: DraftPictureEntity)

    suspend fun deleteTemporaryPicturesList()
}
