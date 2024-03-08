package com.openclassrooms.realestatemanager.ui.details

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailsRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.ui.pictures.PicturesFragment
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_real_estate_fragment), OnMapReadyCallback {

    private val viewModel by viewModels<DetailsViewModel>()
    private val binding by viewBinding { DetailsRealEstateFragmentBinding.bind(it) }
    private var realEstateId: Long = -1

    private lateinit var map: GoogleMap
    private var latitude = 0.0
    private var longitude = 0.0

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

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.staticMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && resources.getBoolean(
                R.bool.isTablet
            )
        ) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.real_estate_details_photo_list_fragment_container,
                    PicturesFragment.newInstance()
                )
                ?.commit()
        }

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            binding.realEstateDetailsTextViewCreationDate.text = it.creationDate
            binding.realEstateDetailsTextViewType.text = it.type
            binding.realEstateDetailsTextViewPrice.text = it.salePrice
            binding.realEstateDetailsTextViewDescription.text = it.description
            binding.realEstateDetailsTextViewFloorArea.text = it.floorArea
            binding.realEstateDetailsTextViewNumberOfRoom.text = it.numberOfRooms.toString()
            binding.realEstateDetailsTextViewLocation.text = it.address
            binding.realEstateDetailsTextViewUpForSale.text = it.upForSaleDate
            binding.realEstateDetailsTextViewAgent.text = it.realEstateAgent

            //todo david gérer ça avec le vm pour éviter les conditions dans la vue
            if (binding.realEstateDetailsTextViewPrice.text == "Préciser le prix") {//todo david gérer hardcoded text
                binding.realEstateDetailsTextViewCurrency.visibility = View.GONE
            }

        }


        viewModel.currentCurrency.observe(viewLifecycleOwner) { currency ->
            binding.realEstateDetailsTextViewCurrency.text = currency
        }

        viewModel.realEstateLocation.observe(viewLifecycleOwner) { latLng ->
            latitude = latLng.latitude
            longitude = latLng.longitude

            val location = LatLng(latitude, longitude)
            map.addMarker(MarkerOptions().position(location).title("Marker"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
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

        viewModel.poiListLiveData.observe(viewLifecycleOwner) { poiList ->
            binding.chipGroup.removeAllViews()
            Log.d("DavidVgn", "onViewCreated: $poiList ${poiList.size}")

            poiList.forEach { pois ->
                val poiItems =
                    pois.split(",") // Diviser la chaîne de caractères en une liste de chaînes de caractères
                poiItems.forEach { poi ->

                    val chip = Chip(requireContext())
                    chip.text = poi.trim()
                    chip.isClickable = false
                    binding.chipGroup.addView(chip)
                }
            }
        }



    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}