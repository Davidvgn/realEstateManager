package com.openclassrooms.realestatemanager.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.ui.utils.Event.Companion.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : SupportMapFragment() {

    private var map: GoogleMap? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                onPermissionGrant()
            }
        }

    companion object {
        fun newInstance() = MapFragment()
    }

    private val viewModel by viewModels<MapViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync { googleMap ->
            map = googleMap

            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun onPermissionGrant() {
        viewModel.viewActionLiveData.observeEvent(viewLifecycleOwner) {
            when (it) {
                is MapViewAction.ZoomTo -> {
                    val latLng = LatLng(it.lat, it.long)
                    map?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            latLng, 15F
                        )
                    )
                }
            }
        }

    }
}

//    companion object {
//        fun newInstance() = MapFragment()
//    }
//
//    private val viewModel by viewModels<MapViewModel>()
//
//    @SuppressLint("MissingPermission")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//
//
//        ActivityCompat.requestPermissions(
//            requireActivity(),
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ), 0
//        )
//
//        getMapAsync { googleMap ->
//
//            viewModel.viewActionLiveData.observeEvent(viewLifecycleOwner) {
//                when (it) {
//                    is MapViewAction.ZoomTo -> {
//                        val latLng = LatLng(it.lat, it.long)
//                        googleMap.animateCamera(
//                            CameraUpdateFactory.newLatLngZoom(
//                                latLng, 15F
//                            )
//                        )
//                    }
//                }
//            }

//            viewModel.viewActionLiveData.observe(viewLifecycleOwner) { event ->
//                event.getContentIfNotHandled()?.let { mapViewAction ->
//                    when (mapViewAction) {
//                        is MapViewAction.ZoomTo -> {
//                            val latLng = LatLng(mapViewAction.lat, mapViewAction.long)
//                            googleMap.animateCamera(
//                                CameraUpdateFactory.newLatLngZoom(
//                                    latLng, 15F
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}