package com.openclassrooms.realestatemanager.domain.real_estates

import com.openclassrooms.realestatemanager.domain.real_estates.model.RealEstateEntity
import javax.inject.Inject

class UpdateRealEstateUseCase
    @Inject
    constructor(
        private val realEstatesRepository: RealEstatesRepository,
    ) {
        suspend fun invoke(realEstateEntity: RealEstateEntity) = realEstatesRepository.updateRealEstate(realEstateEntity)
    }
