package com.openclassrooms.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.location.GetGpsLocationUseCase
import com.openclassrooms.realestatemanager.domain.permission.IsLocationPermissionsGrantedUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.GetRealEstatesListUseCase
import com.openclassrooms.realestatemanager.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val getGpsLocationUseCase: GetGpsLocationUseCase,
        private val isLocationPermissionsGrantedUseCase: IsLocationPermissionsGrantedUseCase,
        private val getRealEstatesListUseCase: GetRealEstatesListUseCase,
    ) : ViewModel() {
        private var hasUserScrolledMap = false

        val viewActionLiveData: LiveData<Event<MapViewAction>> =
            liveData {
                getGpsLocationFlow().collectLatest {
                    if (!hasUserScrolledMap) {
                        emit(Event(MapViewAction.ZoomTo(it.lat, it.long)))
                    }
                }
            }

        val realEstateLiveData: LiveData<MapPoiViewState> =
            liveData {
                getRealEstatesListUseCase.invoke().collectLatest {
                    it.forEach {

                        emit((MapPoiViewState(it.latLng)))
                    }
                }
            }

        fun onUserScrolledMap() {
            hasUserScrolledMap = true
        }

        private fun getGpsLocationFlow() =
            isLocationPermissionsGrantedUseCase.invoke().flatMapLatest { isLocationPermissionsGranted ->
                if (isLocationPermissionsGranted) {
                    getGpsLocationUseCase.invoke()
                } else {
                    emptyFlow()
                }
            }
    }
