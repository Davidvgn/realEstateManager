package com.openclassrooms.realestatemanager.domain.pictures

import com.openclassrooms.realestatemanager.domain.pictures.model.DraftPictureEntity
import javax.inject.Inject

class DeleteTemporaryPictureUseCase
    @Inject
    constructor(
        private val pictureRepository: PicturesRepository,
    ) {
        suspend fun invoke(draftPictureEntity: DraftPictureEntity) {
            pictureRepository.deleteTemporaryPicture(draftPictureEntity)
        }
    }
