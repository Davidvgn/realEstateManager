package com.openclassrooms.realestatemanager.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.ui.utils.Event.Companion.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : SupportMapFragment() {
    companion object {
        fun newInstance() = MapFragment()
    }

    private val viewModel by viewModels<MapViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync { googleMap ->

            viewModel.realEstateLiveData.observe(viewLifecycleOwner) {
                it.latLng?.let { it1 ->
                    MarkerOptions()
                        .position(it1)
                }?.let { it2 ->
                    googleMap.addMarker(
                        it2,
                    )
                }
            }

            viewModel.viewActionLiveData.observeEvent(viewLifecycleOwner) {
                when (it) {
                    is MapViewAction.ZoomTo -> {
                        val latLng = LatLng(it.lat, it.long)
                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng,
                                15F,
                            ),
                        )
                    }
                }
            }
        }
    }
}
