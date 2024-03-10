package com.openclassrooms.realestatemanager.data.pictures

import com.openclassrooms.realestatemanager.domain.pictures.PicturesRepository
import com.openclassrooms.realestatemanager.domain.pictures.model.DraftPictureEntity
import com.openclassrooms.realestatemanager.domain.pictures.model.PicturesEntity
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
        private val draftPictureMutableList = mutableListOf<DraftPictureEntity>()
        private val draftPictureFlow = MutableStateFlow<List<DraftPictureEntity>>(emptyList())

        override fun getPicturesAsFlow(realEstateId: Long): Flow<List<PicturesEntity>> {
            return picturesDao.getPicturesAsFlow(realEstateId)
        }

        override fun getTemporaryPicturesAsFlow(): Flow<List<DraftPictureEntity>> {
            return draftPictureFlow
        }

        override suspend fun addPicture(picturesEntity: PicturesEntity) {
            picturesDao.insert(picturesEntity)
        }

        override suspend fun addTemporaryPicturesList(draftPictureEntity: DraftPictureEntity) {
            draftPictureMutableList.add(draftPictureEntity)
            draftPictureFlow.value = draftPictureMutableList.toList()
        }

        override suspend fun deleteTemporaryPicturesList() {
            draftPictureMutableList.clear()
            draftPictureFlow.value = draftPictureMutableList.toList()
        }
    }
