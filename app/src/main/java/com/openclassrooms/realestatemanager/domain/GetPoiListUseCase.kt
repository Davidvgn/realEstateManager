package com.openclassrooms.realestatemanager.domain

import com.openclassrooms.realestatemanager.domain.details.DetailsRepository
import javax.inject.Inject

class GetPoiListUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository
) {

    fun invoke(realEstateId :Long) = detailsRepository.getPoiListByRealEstateId(realEstateId)
}