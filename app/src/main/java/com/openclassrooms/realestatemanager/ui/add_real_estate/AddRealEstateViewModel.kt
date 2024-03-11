package com.openclassrooms.realestatemanager.ui.add_real_estate

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.pictures.DeleteTemporaryPictureUseCase
import com.openclassrooms.realestatemanager.domain.pictures.model.DraftPictureEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRealEstateViewModel
    @Inject
    constructor(
        private val deleteTemporaryPictureUseCase: DeleteTemporaryPictureUseCase,
    ) : ViewModel() {
        fun deleteTemporaryPicture(
            uri: Uri,
            title: String,
        ) {
            viewModelScope.launch {
                val draftPictureEntity = DraftPictureEntity(uri = uri.toString(), title)
                deleteTemporaryPictureUseCase.invoke(draftPictureEntity)
            }
        }
    }
