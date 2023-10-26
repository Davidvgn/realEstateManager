package com.openclassrooms.realestatemanager.ui.map

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.data.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
private val locationRepository: LocationRepository
): ViewModel () {

}