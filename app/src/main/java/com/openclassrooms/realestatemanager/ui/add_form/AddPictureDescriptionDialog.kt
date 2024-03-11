package com.openclassrooms.realestatemanager.ui.add_form

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddPictureDescriptionDialogFragmentBinding
import com.openclassrooms.realestatemanager.ui.PictureDescriptionListener
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPictureDescriptionDialog(
    private val uri: Uri,
    private val onDescriptionListener: PictureDescriptionListener,
) : DialogFragment(R.layout.add_picture_description_dialog_fragment) {
    private val binding by viewBinding { AddPictureDescriptionDialogFragmentBinding.bind(it) }

    companion object {
        fun newInstance(
            uri: Uri,
            onDescriptionListener: PictureDescriptionListener,
        ) = AddPictureDescriptionDialog(uri, onDescriptionListener)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmationDialogButtonOk.setOnClickListener {
            if (binding.description.text.toString() != "") {
                onDescriptionListener.onDescriptionFilled(uri, binding.description.text.toString())
                dismiss()
            } else {
                Toast.makeText(activity, "Ajouter un titre", Toast.LENGTH_SHORT).show()
            }
        }

        binding.confirmationDialogButtonCancel.setOnClickListener {
            onDescriptionListener.onDescriptionFilled(uri, "")
            dismiss()
        }
    }
}
