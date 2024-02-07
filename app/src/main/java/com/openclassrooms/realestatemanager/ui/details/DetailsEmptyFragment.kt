package com.openclassrooms.realestatemanager.ui.details

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DetailsEmptyFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsEmptyFragment : Fragment(R.layout.details_empty_fragment) {


    private val binding by viewBinding { DetailsEmptyFragmentBinding.bind(it) }

    companion object {
        fun newInstance() = DetailsEmptyFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation: Animation = AnimationUtils.loadAnimation(
            activity,
            R.anim.scale_animation
        )
        binding.detailsEmptyFragmentImageView.startAnimation(animation)
    }
}