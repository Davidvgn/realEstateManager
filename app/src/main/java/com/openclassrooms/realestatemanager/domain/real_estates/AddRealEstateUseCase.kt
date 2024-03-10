package com.openclassrooms.realestatemanager.domain.real_estates

import javax.inject.Inject

class AddRealEstateUseCase
    @Inject
    constructor(
        private val realEstatesRepository: RealEstatesRepository,
    ) {
        suspend fun invoke(realEstate: RealEstateEntity): Long {
            return realEstatesRepository.add(realEstate)
        }
    }
