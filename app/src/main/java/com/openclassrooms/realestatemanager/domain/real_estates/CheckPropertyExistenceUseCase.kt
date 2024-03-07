package com.openclassrooms.realestatemanager.domain.real_estates

import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CheckPropertyExistenceUseCase  @Inject constructor(
    private val realEstatesRepository: RealEstatesRepository
) {

      suspend fun invoke(realEstateId: Long): Boolean {
          val realEstateList = realEstatesRepository.getRealEstatesAsFlow().firstOrNull()
          return realEstateList?.any { it.id == realEstateId } ?: false

      }


}