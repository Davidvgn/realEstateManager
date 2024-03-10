package com.openclassrooms.realestatemanager.domain.pictures

import javax.inject.Inject

class AddPicturesUseCase
    @Inject
    constructor(
        private val picturesRepository: PicturesRepository,
    ) {
        suspend fun invoke(picturesEntity: PicturesEntity) {
            picturesRepository.addPicture(picturesEntity)
        }
    }
