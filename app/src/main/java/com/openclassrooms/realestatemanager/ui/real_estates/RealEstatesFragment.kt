package com.openclassrooms.realestatemanager.ui.real_estates

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstatesFragmentBinding
import com.openclassrooms.realestatemanager.domain.RealEstateEntity
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstatesFragment : Fragment(R.layout.real_estates_fragment) {

    companion object {
        fun newInstance() = RealEstatesFragment()
    }

    private val binding by viewBinding { RealEstatesFragmentBinding.bind(it) }
    private val viewModel by viewModels<RealEstatesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RealEstatesAdapter()
        binding.realEstateRecyclerView.adapter = adapter
        val resourceId: Int = R.drawable.sample_image

        val testRealEstatesList: List<RealEstatesViewSateItem> = listOf(
            RealEstatesViewSateItem(0,
                "test1",
                "Lyon", 10_000 , Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId"), ""),
            RealEstatesViewSateItem(0,
                "test2",
                "New-York", 10_000 , Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId"), ""),
            RealEstatesViewSateItem(0,
                "test3",
                "Berlin", 10_000 , Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId"), ""),
            RealEstatesViewSateItem(0,
                "test4",
                "Barcelone", 10_000 , Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId"), ""),
        )
        adapter.submitList(testRealEstatesList)

    }
}