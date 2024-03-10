package com.openclassrooms.realestatemanager.data.pictures

import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.pictures.PicturesRepository
import com.openclassrooms.realestatemanager.domain.pictures.draft_picture.DraftPictureEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepositoryImpl
    @Inject
    constructor(
        private val picturesDao: PicturesDao,
    ) : PicturesRepository {
        private val temporaryPicturesList = mutableListOf<DraftPictureEntity>()
        private val temporaryPicturesFlow = MutableStateFlow<List<DraftPictureEntity>>(emptyList())

        override fun getPicturesAsFlow(realEstateId: Long): Flow<List<PicturesEntity>> {
            return picturesDao.getPicturesAsFlow(realEstateId)
        }

        override fun getTemporaryPicturesAsFlow(): Flow<List<DraftPictureEntity>> {
            return temporaryPicturesFlow
        }

        override suspend fun addPicture(picturesEntity: PicturesEntity) {
            picturesDao.insert(picturesEntity)
        }

        override suspend fun addTemporaryPicturesList(draftPictureEntity: DraftPictureEntity) {
            temporaryPicturesList.add(draftPictureEntity)
            temporaryPicturesFlow.value = temporaryPicturesList.toList()
        }

        override suspend fun deleteTemporaryPicturesList() {
            temporaryPicturesList.clear()
            temporaryPicturesFlow.value = temporaryPicturesList.toList()
        }
    }
