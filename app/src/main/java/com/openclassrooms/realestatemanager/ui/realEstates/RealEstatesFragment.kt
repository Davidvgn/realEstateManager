package com.openclassrooms.realestatemanager.ui.realEstates

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstatesFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding

class RealEstatesFragment: Fragment(R.layout.real_estates_fragment) {

    companion object {
        fun newInstance() = RealEstatesFragment()
    }

    private val binding by viewBinding { RealEstatesFragmentBinding.bind(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter= RealEstatesAdapter()
        binding.estateRecyclerView.adapter = adapter
    }
}