package com.openclassrooms.realestatemanager.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.pictures.PicturesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateDetailsFragment: Fragment(R.layout.real_estate_details_fragment) {

    companion object {
        fun newInstance() = RealEstateDetailsFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && resources.getBoolean(R.bool.isTablet)) {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.real_estate_details_photo_list_fragment_container, PicturesFragment.newInstance())
                    ?.commit()
        }
    }
}