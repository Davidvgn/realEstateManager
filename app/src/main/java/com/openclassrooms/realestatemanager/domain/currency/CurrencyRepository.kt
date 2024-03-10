package com.openclassrooms.realestatemanager.domain.currency

interface CurrencyRepository {
    suspend fun getCurrentCurrency(): String

    suspend fun setCurrentCurrency(currency: String)
}
