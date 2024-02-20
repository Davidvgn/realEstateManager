package com.openclassrooms.realestatemanager.domain.real_estates

import javax.inject.Inject

class DeleteRealEstateUseCase @Inject constructor(
    private val realEstatesRepository: RealEstatesRepository) {

    suspend fun invoke(realEstateId: Long) {
        realEstatesRepository.delete(realEstateId)
    }

}