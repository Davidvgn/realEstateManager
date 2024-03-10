package com.openclassrooms.realestatemanager.domain.details

import javax.inject.Inject

class GetPoiListUseCase
    @Inject
    constructor(
        private val detailsRepository: DetailsRepository,
    ) {
        fun invoke(realEstateId: Long) = detailsRepository.getPoiListByRealEstateId(realEstateId)
    }
