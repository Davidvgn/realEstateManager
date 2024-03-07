package com.openclassrooms.realestatemanager.domain

interface CurrencyRepository {
    suspend fun getCurrentCurrency(): String
    suspend fun setCurrentCurrency(currency: String)
}
