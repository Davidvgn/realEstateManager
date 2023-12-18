package com.openclassrooms.realestatemanager.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.domain.location.GetGpsLocationUseCase
import com.openclassrooms.realestatemanager.domain.permission.GetGrantedPermissionsUseCase
import com.openclassrooms.realestatemanager.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getGpsLocationUseCase: GetGpsLocationUseCase,
    private val getGrantedPermissionsUseCase: GetGrantedPermissionsUseCase,
) : ViewModel() {

    private var hasUserScrolledMap = false

    val viewActionLiveData: LiveData<Event<MapViewAction>> = liveData {
        getGpsLocationFlow().collectLatest {
            if (!hasUserScrolledMap) {
                emit(Event(MapViewAction.ZoomTo(it.lat, it.long)))
            }
        }
    }

    fun onUserScrolledMap() {
        hasUserScrolledMap = true
    }

    private fun getGpsLocationFlow() = getGrantedPermissionsUseCase.invoke()
        .flatMapLatest {
            getGpsLocationUseCase.invoke()
        }
}

