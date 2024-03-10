package com.openclassrooms.realestatemanager.domain.pictures

import com.openclassrooms.realestatemanager.domain.pictures.draft_picture.DraftPictureEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTemporaryPicturesUseCase
    @Inject
    constructor(
        private val pictureRepository: PicturesRepository,
    ) {
        fun invoke(): Flow<List<DraftPictureEntity>> = pictureRepository.getTemporaryPicturesAsFlow()
    }
