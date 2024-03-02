package com.openclassrooms.realestatemanager.domain.pictures

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTemporaryPicturesUseCase @Inject constructor(
    private val pictureRepository: PicturesRepository
){
    fun invoke(): Flow<List<PicturesEntity>> = pictureRepository.getPicturesNoDAO()
}