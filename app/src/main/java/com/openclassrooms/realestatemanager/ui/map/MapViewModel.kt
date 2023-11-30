package com.openclassrooms.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.location.GetGpsLocationUseCase
import com.openclassrooms.realestatemanager.domain.permission.GetGrantedPermissionsUseCase
import com.openclassrooms.realestatemanager.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
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


    val viewStateLiveData: LiveData<MapPoiViewState> = liveData {
        emit(
            MapPoiViewState(
                48.8566,
                2.3522
            )
        )

        getGpsLocationFlow().collect {
            emit(
                MapPoiViewState(
                    it.lat,
                    it.long,
                )
            )
        }
    }

    fun onUserScrolledMap() {
        hasUserScrolledMap = true
    }

    private fun getGpsLocationFlow() = getGrantedPermissionsUseCase.invoke().filter { permission ->
        permission.any {
            it.permission == "android.permission.ACCESS_FINE_LOCATION"
                || it.permission == "android.permission.ACCESS_COARSE_LOCATION"
        }
    }.flatMapLatest {
        getGpsLocationUseCase.invoke()
    }
}