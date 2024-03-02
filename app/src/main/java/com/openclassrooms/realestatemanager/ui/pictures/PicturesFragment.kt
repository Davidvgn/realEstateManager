package com.openclassrooms.realestatemanager.ui.pictures

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.PicturesFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicturesFragment : Fragment(R.layout.pictures_fragment) {

    private val binding by viewBinding { PicturesFragmentBinding.bind(it) }
    private val viewModel by viewModels<PicturesViewModel>()

    companion object {
        fun newInstance() = PicturesFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PicturesAdapter()
        binding.addFormRvPictures.adapter = adapter

        viewModel.pictureViewStateLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        flexboxLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
        }
        binding.addFormRvPictures.layoutManager = flexboxLayoutManager

    }
}