package com.openclassrooms.realestatemanager.ui.pictures

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.PicturesFragmentBinding
import com.openclassrooms.realestatemanager.ui.OnPictureClickedListener
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicturesFragment : Fragment(R.layout.pictures_fragment) {
    private val binding by viewBinding { PicturesFragmentBinding.bind(it) }
    private val viewModel by viewModels<PicturesViewModel>()

    private lateinit var onPictureClickedListener: OnPictureClickedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPictureClickedListener) {
            onPictureClickedListener = context
        } else {
            throw RuntimeException("$context must implement OnPictureClickedListener")
        }
    }

    companion object {
        fun newInstance() = PicturesFragment()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PicturesAdapter(onPictureClickedListener)
        binding.addFormRvPictures.adapter = adapter

        viewModel.pictureViewStateLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
