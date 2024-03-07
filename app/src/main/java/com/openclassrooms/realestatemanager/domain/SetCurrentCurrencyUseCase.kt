package com.openclassrooms.realestatemanager.domain

import javax.inject.Inject

class SetCurrentCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(currency: String) {
        currencyRepository.setCurrentCurrency(currency)
    }
}
