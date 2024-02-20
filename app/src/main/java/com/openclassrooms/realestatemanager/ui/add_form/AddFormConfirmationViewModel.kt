package com.openclassrooms.realestatemanager.ui.add_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.real_estates.DeleteRealEstateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFormConfirmationViewModel @Inject constructor(
    private val deleteRealEstateUseCase: DeleteRealEstateUseCase
): ViewModel() {


    fun deleteRealEstate(realEstateId: Long) {
        viewModelScope.launch {
            deleteRealEstateUseCase.invoke(realEstateId)
        }
    }


}