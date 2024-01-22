package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateDetailsFragment: Fragment(R.layout.real_estate_details_fragment) {
    companion object {
        fun newInstance() = RealEstateDetailsFragment()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}