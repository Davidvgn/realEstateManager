package com.openclassrooms.realestatemanager.ui.add_form

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPictureDescriptionViewModel @Inject constructor(
): ViewModel() {
    fun validateDescription(text: String?) {
        Log.d("DavidVgn", "validateDescription: $text")
    }
}