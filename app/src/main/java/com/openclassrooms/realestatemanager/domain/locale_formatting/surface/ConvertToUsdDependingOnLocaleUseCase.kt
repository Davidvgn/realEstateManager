package com.openclassrooms.realestatemanager.domain.locale_formatting.surface

import com.openclassrooms.realestatemanager.domain.CurrencyRepository
import com.openclassrooms.realestatemanager.domain.locale_formatting.GetLocaleUseCase
import com.openclassrooms.realestatemanager.domain.locale_formatting.HumanReadableRepository
import java.math.BigDecimal
import java.util.Locale
import javax.inject.Inject

class ConvertToUsdDependingOnLocaleUseCase @Inject constructor(
    private val humanReadableRepository: HumanReadableRepository,
    private val currencyRepository: CurrencyRepository,
    private val getLocaleUseCase: GetLocaleUseCase,
) {
    /**
     * @return BigDecimal of the price converted to USD depending on locale
     * (price saved in database is in USD)
     */
//    suspend fun invoke(price: BigDecimal): BigDecimal {
//        val locale = getLocaleUseCase.invoke()
//        if (price == BigDecimal.ZERO) return BigDecimal.ZERO
//
//        return if (locale == Locale.FRANCE) {
//            when (val currencyWrapper = currencyRepository.getCurrentCurrency()) {
//                is CurrencyRateWrapper.Success -> {
//                    humanReadableRepository.convertEuroToDollarRoundedHalfUp(
//                        price,
//                        currencyWrapper.currencyRateEntity.usdToEuroRate
//                    )
//                }
//
//                is CurrencyRateWrapper.Error -> {
//                    humanReadableRepository.convertEuroToDollarRoundedHalfUp(
//                        price,
//                        currencyWrapper.fallbackUsToEuroRate
//                    )
//                }
//            }
//        } else {
//            price
//        }
//    }
}
