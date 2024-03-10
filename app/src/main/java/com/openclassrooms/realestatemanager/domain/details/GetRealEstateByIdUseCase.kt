package com.openclassrooms.realestatemanager.domain.details

import com.openclassrooms.realestatemanager.domain.real_estates.model.RealEstateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRealEstateByIdUseCase
    @Inject
    constructor(
        private val detailsRepository: DetailsRepository,
    ) {
        fun invoke(id: Long): Flow<RealEstateEntity> = detailsRepository.getRealEstatesByIdAsFlow(id)
    }
