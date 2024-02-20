package com.openclassrooms.realestatemanager.domain.pictures
import javax.inject.Inject

class AddPicturesUseCase @Inject constructor(
    private val pictureRepository: PicturesRepository
) {
     suspend fun invoke(picturesEntity: PicturesEntity) {
        pictureRepository.addPicture(picturesEntity)
    }
}