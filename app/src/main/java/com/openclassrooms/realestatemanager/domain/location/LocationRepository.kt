package com.openclassrooms.realestatemanager.domain.location

import com.openclassrooms.realestatemanager.domain.location.model.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationAsFlow(): Flow<LocationEntity>
}
