package com.openclassrooms.realestatemanager.ui.map

sealed class MapViewAction {
    data class ZoomTo(
        val lat: Double,
        val long: Double,
        val zoom: Double? = null,
    ) : MapViewAction()
}