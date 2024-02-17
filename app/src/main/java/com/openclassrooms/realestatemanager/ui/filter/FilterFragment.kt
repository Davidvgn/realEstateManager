package com.openclassrooms.realestatemanager.ui.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment: Fragment(R.layout.filter_real_estate_fragment) {
    //Todo david : faire un drawerLayout pour afficher la vue

    companion object {
        fun newInstance() = FilterFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}