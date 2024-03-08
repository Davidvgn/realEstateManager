package com.openclassrooms.realestatemanager.ui.add_form

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddPictureDescriptionDialogFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPictureDescriptionDialog : DialogFragment(R.layout.add_picture_description_dialog_fragment){

    private val binding by viewBinding { AddPictureDescriptionDialogFragmentBinding.bind(it) }
    private val viewModel by viewModels<AddPictureDescriptionViewModel>()


    companion object{
    fun newInstance() = AddPictureDescriptionDialog()
}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmationDialogButtonOk.setOnClickListener {
                viewModel.validateDescription(binding.description.text.toString())
                dismiss()
        }

        binding.confirmationDialogButtonCancel.setOnClickListener {
            dismiss()
        }
    }
}