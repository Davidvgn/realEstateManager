package com.openclassrooms.realestatemanager.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.openclassrooms.realestatemanager.domain.currency.CurrencyRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryDataStore
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : CurrencyRepository {
        companion object {
            private val CURRENCY_KEY = stringPreferencesKey("currency_key")
            private const val DEFAULT_CURRENCY = "$"
        }

        override suspend fun getCurrentCurrency(): String {
            val preferences = dataStore.data.first()
            return preferences[CURRENCY_KEY] ?: DEFAULT_CURRENCY
        }

        override suspend fun setCurrentCurrency(currency: String) {
            dataStore.edit { preferences ->
                preferences[CURRENCY_KEY] = currency
            }
        }
    }
