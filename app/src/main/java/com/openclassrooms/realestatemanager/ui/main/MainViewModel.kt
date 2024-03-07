package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.agent.AgentEntity
import com.openclassrooms.realestatemanager.domain.agent.GetAgentsUseCase
import com.openclassrooms.realestatemanager.domain.permission.RefreshPermissionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val refreshPermissionsUseCase: RefreshPermissionsUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
    private val getAgentsUseCase: GetAgentsUseCase
): ViewModel(){

    private val _currentCurrency = MutableLiveData<String>()

    private val _agentsLiveData = MutableLiveData<List<AgentEntity>>()
    val agentsLiveData: LiveData<List<AgentEntity>> = _agentsLiveData

    private var agentId: Long? = null

        init {
        viewModelScope.launch {
            getCurrentCurrencyUseCase().let { currency ->
                _currentCurrency.value = currency
            }
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
}