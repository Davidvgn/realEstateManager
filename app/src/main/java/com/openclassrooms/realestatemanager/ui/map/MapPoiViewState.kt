package com.openclassrooms.realestatemanager.ui.map

import com.google.android.gms.maps.model.LatLng

data class MapPoiViewState(
    val id: Long,
    val latLng: LatLng,
    val address: String,
)
