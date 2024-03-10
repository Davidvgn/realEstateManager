package com.openclassrooms.realestatemanager.domain.pictures

import com.openclassrooms.realestatemanager.domain.pictures.model.PicturesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPicturesUseCase
    @Inject
    constructor(
        private val pictureRepository: PicturesRepository,
    ) {
        fun invoke(realEstateId: Long): Flow<List<PicturesEntity>> = pictureRepository.getPicturesAsFlow(realEstateId)
    }
