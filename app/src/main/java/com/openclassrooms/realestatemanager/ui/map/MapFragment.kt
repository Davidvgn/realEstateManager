package com.openclassrooms.realestatemanager.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.ui.OnRealEstateClickedListener
import com.openclassrooms.realestatemanager.ui.utils.Event.Companion.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : SupportMapFragment() {
    companion object {
        fun newInstance() = MapFragment()
    }

    private val viewModel by viewModels<MapViewModel>()

    @SuppressLint("PotentialBehaviorOverride")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync { googleMap ->

            viewModel.realEstateLiveData.observe(viewLifecycleOwner) { mapPoiViewState ->
                googleMap.addMarker(
                    MarkerOptions()
                        .position(mapPoiViewState.latLng)
                        .title(mapPoiViewState.address),
                )

                viewModel.realEstateLiveDataList.observe(viewLifecycleOwner) {
                }

                googleMap.setOnInfoWindowClickListener { marker ->
                    val markerTitle = marker.title

                    viewModel.realEstateLiveDataList.value?.let { realEstateList ->
                        val matchingRealEstate = realEstateList.find { it.address == markerTitle }
                        matchingRealEstate?.let { mapPoiViewState ->
                            val realEstateId = mapPoiViewState.id
                            (requireActivity() as? OnRealEstateClickedListener)?.onRealEstateClicked(realEstateId)
                        }
                    }
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
