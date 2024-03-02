package com.openclassrooms.realestatemanager.domain.pictures

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPicturesUseCase @Inject constructor(
    private val pictureRepository: PicturesRepository
){
//    fun invoke(id: Long): Flow<List<PicturesEntity>> = pictureRepository.getPicturesAsFlow(id)
    fun invoke(): Flow<List<PicturesEntity>> = pictureRepository.getPicturesAsFlow()
}