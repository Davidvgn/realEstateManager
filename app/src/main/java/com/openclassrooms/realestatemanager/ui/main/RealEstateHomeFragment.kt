package com.openclassrooms.realestatemanager.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.RealEstateDetailsFragment
import com.openclassrooms.realestatemanager.ui.real_estates.RealEstatesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateHomeFragment : Fragment(R.layout.real_estate_home_fragment) {

    companion object {
        fun newInstance() = RealEstateHomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.estate_list_fragment_container, RealEstatesFragment.newInstance())
            ?.commit()

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && resources.getBoolean(R.bool.isTablet)) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.details_fragment_container, RealEstateDetailsFragment.newInstance())
                ?.commit()
        }
    }
}