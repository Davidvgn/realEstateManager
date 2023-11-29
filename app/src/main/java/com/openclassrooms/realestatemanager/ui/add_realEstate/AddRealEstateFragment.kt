package com.openclassrooms.realestatemanager.ui.add_realEstate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.NavigationListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateFragment: Fragment(R.layout.add_real_estate_fragment) {

    companion object{
        fun newInstance() = AddRealEstateFragment()
    }

    private lateinit var navigationListener: NavigationListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = context as NavigationListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}