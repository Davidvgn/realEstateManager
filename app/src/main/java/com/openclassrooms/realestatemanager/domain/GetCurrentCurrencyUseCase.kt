package com.openclassrooms.realestatemanager.domain

import javax.inject.Inject

class GetCurrentCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(): String{
        return currencyRepository.getCurrentCurrency()
    }
}

