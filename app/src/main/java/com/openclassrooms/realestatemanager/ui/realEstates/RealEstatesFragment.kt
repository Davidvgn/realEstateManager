package com.openclassrooms.realestatemanager.ui.realEstates

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstatesFragmentBinding
import com.openclassrooms.realestatemanager.ui.NavigationListener
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstatesFragment: Fragment(R.layout.real_estates_fragment) {

    companion object {
        fun newInstance() = RealEstatesFragment()
    }

    private val binding by viewBinding { RealEstatesFragmentBinding.bind(it) }
    private val viewModel by viewModels<RealEstatesViewModel>()

    private lateinit var navigationListener: NavigationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = context as NavigationListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter= RealEstatesAdapter()
        binding.realEstateRecyclerView.adapter = adapter
        binding.realEstateFloatingActionButtonAddTask.setOnClickListener { viewModel.onAddButtonClicked() }

        viewModel.singleLiveEvent.observe(viewLifecycleOwner){realEstateEvent ->
            when(realEstateEvent){
                RealEstatesEvent.NavigateToAddRealEstate -> navigationListener.displayAddRealEstateDialog()
            }
        }
    }
}