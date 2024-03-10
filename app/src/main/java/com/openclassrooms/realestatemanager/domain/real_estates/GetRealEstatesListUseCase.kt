package com.openclassrooms.realestatemanager.domain.real_estates

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRealEstatesListUseCase
    @Inject
    constructor(
        private val realEstatesRepository: RealEstatesRepository,
    ) {
        fun invoke(): Flow<List<RealEstateEntity>> = realEstatesRepository.getRealEstatesAsFlow()

        fun isListEmpty(): Flow<Boolean> = realEstatesRepository.isListEmptyAsFlow()
    }
