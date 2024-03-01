package com.openclassrooms.realestatemanager.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor() : ViewModel() {


    companion object {
        private val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"

        fun navigate(realEstateId: Long): Fragment {
            val fragment = DetailsFragment.newInstance()
            val args = Bundle().apply {
                putLong(KEY_REAL_ESTATE_ID, realEstateId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}