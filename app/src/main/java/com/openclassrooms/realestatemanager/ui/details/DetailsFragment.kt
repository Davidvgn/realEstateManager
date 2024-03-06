package com.openclassrooms.realestatemanager.ui.details

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailsRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.ui.pictures.PicturesFragment
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.details_real_estate_fragment), OnMapReadyCallback {

    private val viewModel by viewModels<DetailsViewModel>()
    private val binding by viewBinding { DetailsRealEstateFragmentBinding.bind(it) }
    private var realEstateId: Long = -1

    private lateinit var map: GoogleMap
    private val latitude = 45.7577
    private val longitude = 4.8320

    companion object {
        fun newInstance() = DetailsFragment()
        private const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            realEstateId = it.getLong(KEY_REAL_ESTATE_ID, -1)
        }
        viewModel.initRealEstateId(id = realEstateId)

        val mapFragment = childFragmentManager.findFragmentById(R.id.staticMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && resources.getBoolean(R.bool.isTablet)) {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.real_estate_details_photo_list_fragment_container, PicturesFragment.newInstance())
                    ?.commit()
        }

        viewModel.viewStateLiveData.observe(viewLifecycleOwner){
            binding.realEstateDetailsTextViewCreationDate.text = it.creationDate
            binding.realEstateDetailsTextViewType.text = it.type
            binding.realEstateDetailsTextViewDescription.text = it.description
            binding.realEstateDetailsTextViewFloorArea.text = it.floorArea
            binding.realEstateDetailsTextViewNumberOfRoom.text = it.numberOfRooms.toString()
            binding.realEstateDetailsTextViewLocation.text = it.address
        }

//        viewModel.realEstatePictures.observe(viewLifecycleOwner){ uriString ->
//            val uri = Uri.parse(uriString)
//            val imageView = ImageView(requireContext())
//            imageView.setImageURI(uri)
//
//            val layoutParams = FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//            )
//
//            binding.realEstateDetailsPhotoListFragmentContainer.addView(imageView, layoutParams)
//        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val location = LatLng(latitude, longitude)
        map.addMarker(MarkerOptions().position(location).title("Marker"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))    }
}