package com.openclassrooms.realestatemanager.domain.pictures

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPicturesUseCase  @Inject constructor(
    private val pictureRepository: PicturesRespository
){
    fun invoke(): Flow<List<PicturesEntity>> = pictureRepository.getPicturesAsFlow()
}