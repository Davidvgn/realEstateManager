package com.openclassrooms.realestatemanager.ui.update

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.UpdateRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateRealEstateFragment : Fragment(R.layout.update_real_estate_fragment) {
    private val binding by viewBinding { UpdateRealEstateFragmentBinding.bind(it) }
    private val viewModel by viewModels<UpdateRealEstateViewModel>()

    companion object {
        const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"

        fun newInstance(realEstateId: Long): UpdateRealEstateFragment {
            val fragment = UpdateRealEstateFragment()
            val args = Bundle()
            args.putLong(KEY_REAL_ESTATE_ID, realEstateId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val realEstateId = arguments?.getLong(KEY_REAL_ESTATE_ID, -1) ?: -1

        viewModel.initRealEstateId(id = realEstateId)

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { updateViewState ->
            for (chip in binding.updateRealEstateChipGroupType.children) {
                if (chip is Chip) {
                    chip.isChecked = (chip.text == updateViewState.type)
                }
            }

            if (updateViewState.status == "Sold") {
                binding.updateRealEstateRadioButtonSold.isChecked = true
            }

            binding.updateRealEstateTextInputEditTextPrice.setText(updateViewState.salePrice)
            binding.updateRealEstateTextInputEditTextDescription.setText(updateViewState.description)
        }
    }
}
