package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.CheckNetworkConnectionUseCase
import com.openclassrooms.realestatemanager.domain.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.agent.AgentEntity
import com.openclassrooms.realestatemanager.domain.agent.GetAgentsUseCase
import com.openclassrooms.realestatemanager.domain.permission.RefreshPermissionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val refreshPermissionsUseCase: RefreshPermissionsUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
    private val getAgentsUseCase: GetAgentsUseCase,
    private val checkNetworkConnectionUseCase: CheckNetworkConnectionUseCase

): ViewModel(){

    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable

    private val _currentCurrency = MutableLiveData<String>()

    private val _agentsLiveData = MutableLiveData<List<AgentEntity>>()
    val agentsLiveData: LiveData<List<AgentEntity>> = _agentsLiveData

    private var agentId: Long? = null

    companion object {
        private const val INTERVAL_CHECK_NETWORK = 5000L
    }

    init {
        viewModelScope.launch {
            getCurrentCurrencyUseCase().let { currency ->
                _currentCurrency.value = currency
            }
            checkNetworkStatus()
        }
    }

    fun fetchAgents() {
        viewModelScope.launch {
            val agents = getAgentsUseCase()
            _agentsLiveData.postValue(agents)
        }
    }

    fun onAgentSelected(id: Long) {
        this.agentId = id

    }

    fun onPermissionUpdated() {
        refreshPermissionsUseCase.invoke()
    }

    private fun checkNetworkStatus() {
        viewModelScope.launch {
            val currentNetworkStatus = checkNetworkConnectionUseCase.invoke()
            _isNetworkAvailable.value = currentNetworkStatus

            delay(INTERVAL_CHECK_NETWORK)
            checkNetworkStatus()
        }
    }

}