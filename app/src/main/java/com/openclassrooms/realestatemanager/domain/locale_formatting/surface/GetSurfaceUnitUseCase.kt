package com.openclassrooms.realestatemanager.domain.locale_formatting.surface

import com.openclassrooms.realestatemanager.domain.locale_formatting.HumanReadableRepository
import javax.inject.Inject

class GetSurfaceUnitUseCase @Inject constructor(
    private val humanReadableRepository: HumanReadableRepository,
) {
    fun invoke(): SurfaceUnitType = humanReadableRepository.getLocaleSurfaceUnitFormatting()
}