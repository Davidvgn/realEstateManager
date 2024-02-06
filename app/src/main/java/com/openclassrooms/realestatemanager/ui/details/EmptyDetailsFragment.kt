package com.openclassrooms.realestatemanager.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmptyDetailsFragment: Fragment(R.layout.details_empty_fragment) {

    companion object {
        fun newInstance() = EmptyDetailsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}