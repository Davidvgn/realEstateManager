package com.openclassrooms.realestatemanager.domain.pictures

import javax.inject.Inject

class DeleteTemporaryPicturesListUseCase @Inject constructor(
    private val picturesRepository: PicturesRepository
) {

    suspend fun invoke() = picturesRepository.deleteTemporaryPicturesList()
}