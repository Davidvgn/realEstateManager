package com.openclassrooms.realestatemanager.ui.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.UpdateRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateRealEstateFragment : Fragment(R.layout.update_real_estate_fragment) {
    private val binding by viewBinding { UpdateRealEstateFragmentBinding.bind(it) }
    private val viewModel by viewModels<UpdateRealEstateViewModel>()

    companion object {
        val newInstance = UpdateRealEstateFragment()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}
