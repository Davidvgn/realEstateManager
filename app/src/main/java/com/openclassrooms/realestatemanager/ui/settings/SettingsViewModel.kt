package com.openclassrooms.realestatemanager.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.SetCurrentCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setCurrentCurrencyUseCase: SetCurrentCurrencyUseCase
) : ViewModel() {

    private val _currentCurrency = MutableLiveData<String>()

    fun setCurrentCurrency(currency: String) {
        viewModelScope.launch {
            setCurrentCurrencyUseCase(currency)
            _currentCurrency.value = currency
        }
    }
}
