package com.openclassrooms.realestatemanager.ui.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.ui.utils.Event.Companion.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : SupportMapFragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        viewModel.onPermissionUpdated()
    }

    private val viewModel by viewModels<MapViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync { googleMap ->
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            viewModel.viewActionLiveData.observeEvent(viewLifecycleOwner) {
                when (it) {
                    is MapViewAction.ZoomTo -> {
                        val latLng = LatLng(it.lat, it.long)
                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng, 15F
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.onPermissionUpdated()
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