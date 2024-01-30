package com.openclassrooms.realestatemanager.ui.addform

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ConfirmationDialogFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFormConfirmationDialog : DialogFragment(R.layout.confirmation_dialog_fragment) {

    private val binding by viewBinding { ConfirmationDialogFragmentBinding.bind(it) }

    companion object {
        fun newInstance() = AddFormConfirmationDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmationDialogButtonYes.setOnClickListener {
            activity?.finish()
        }

        binding.confirmationDialogButtonNo.setOnClickListener {
            dismiss()
        }
    }
}