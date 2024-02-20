package com.openclassrooms.realestatemanager.domain.pictures

import javax.inject.Inject

class AddPicturesUseCase @Inject constructor(
    private val pictureRepository: PicturesRepository
) {
    suspend fun invoke(pictures: List<PicturesEntity>) {
        pictureRepository.addPictures(pictures)
    }

}