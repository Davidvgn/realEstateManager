package com.openclassrooms.realestatemanager.ui.real_estates_home

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.details.EmptyDetailsFragment
import com.openclassrooms.realestatemanager.ui.details.RealEstateDetailsFragment
import com.openclassrooms.realestatemanager.ui.real_estates.RealEstatesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstateHomeFragment : Fragment(R.layout.real_estate_home_fragment) {

    private val viewModel by viewModels<RealEstatesHomeViewModel>()

    companion object {
        fun newInstance() = RealEstateHomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.estate_list_fragment_container, RealEstatesFragment.newInstance())
            ?.commit()

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && resources.getBoolean(
                R.bool.isTablet
            )
        ) {

            viewModel.isListEmptyLiveData.observe(viewLifecycleOwner) { bool ->
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.details_fragment_container,
                        if (bool) EmptyDetailsFragment.newInstance() else RealEstateDetailsFragment.newInstance()
                    )
                    ?.commit()

            }

        }
    }
}