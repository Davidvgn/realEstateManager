package com.openclassrooms.realestatemanager.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.location.GetGpsLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getGpsLocationUseCase: GetGpsLocationUseCase,
) : ViewModel() {

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