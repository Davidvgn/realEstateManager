package com.openclassrooms.realestatemanager.ui.add_realEstate

import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateDialogFragment: DialogFragment(R.layout.add_real_estate_dialog_fragment) {
    companion object{
        fun newInstance() = AddRealEstateDialogFragment()
    }
}