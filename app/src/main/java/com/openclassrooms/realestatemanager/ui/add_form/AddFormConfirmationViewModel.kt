package com.openclassrooms.realestatemanager.ui.add_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.pictures.DeleteTemporaryPicturesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFormConfirmationViewModel
    @Inject
    constructor(
        private val deleteTemporaryPicturesListUseCase: DeleteTemporaryPicturesListUseCase,
    ) : ViewModel() {
        fun deletePictures() {
            viewModelScope.launch {
                deleteTemporaryPicturesListUseCase.invoke()
            }
        }
    }
