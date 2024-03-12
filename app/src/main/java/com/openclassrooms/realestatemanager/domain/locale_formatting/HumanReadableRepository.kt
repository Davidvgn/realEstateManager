package com.openclassrooms.realestatemanager.domain.locale_formatting

import com.openclassrooms.realestatemanager.domain.locale_formatting.surface.SurfaceUnitType
import java.math.BigDecimal
import java.util.Locale

interface HumanReadableRepository {
    fun getLocale(): Locale

    fun convertSquareFeetToSquareMetersRoundedHalfUp(squareFeet: BigDecimal): BigDecimal

    fun convertSquareMetersToSquareFeetRoundedHalfUp(squareMeters: BigDecimal): BigDecimal

    fun getLocaleSurfaceUnitFormatting(): SurfaceUnitType

    fun formatRoundedPriceToHumanReadable(price: BigDecimal): String
}
