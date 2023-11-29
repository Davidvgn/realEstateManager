package com.openclassrooms.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.domain.location.GetGpsLocationUseCase
import com.openclassrooms.realestatemanager.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getGpsLocationUseCase: GetGpsLocationUseCase,
    ) : ViewModel() {


    val cameraUpdate: LiveData<LatLng> =
        liveData {//todo david obserser si on a la permission (créer permissionRepo et déplacer celui qui est dans la vue dedans
            getGpsLocationUseCase.invoke().collectLatest {
                emit(LatLng(it.lat, it.long))
            }
        }


    val viewStateLiveData: LiveData<MapPoiViewState> = liveData {
        emit(
            MapPoiViewState(
                48.8566,
                2.3522
            )
        )

        getGpsLocationUseCase.invoke().collect {
            emit(
                MapPoiViewState(
                    it.lat,
                    it.long,
                )
            )
        }
    }
}