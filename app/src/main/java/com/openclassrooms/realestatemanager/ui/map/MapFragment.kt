package com.openclassrooms.realestatemanager.ui.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment: SupportMapFragment() {

   companion object{
       fun newInstance() = MapFragment()
   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            getMapAsync { googleMap ->
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(48.8566,2.3522))
                )
            }

    }
}