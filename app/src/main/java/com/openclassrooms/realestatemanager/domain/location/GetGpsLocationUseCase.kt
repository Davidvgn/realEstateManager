package com.openclassrooms.realestatemanager.domain.location

import com.openclassrooms.realestatemanager.domain.location.model.LocationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGpsLocationUseCase
    @Inject
    constructor(
        private val locationRepository: LocationRepository,
    ) {
        fun invoke(): Flow<LocationEntity> = locationRepository.getLocationAsFlow()
    }
