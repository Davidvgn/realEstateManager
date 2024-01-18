package com.openclassrooms.realestatemanager.ui.real_estates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.real_estates.GetRealEstatesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor(
    getRealEstatesListUseCase: GetRealEstatesListUseCase
) : ViewModel() {

    val list: List<RealEstatesViewSateItem> = emptyList()

    val viewStateLiveData: LiveData<List<RealEstatesViewSateItem>> = liveData {
        getRealEstatesListUseCase.invoke()
        emit(listOf(RealEstatesViewSateItem.EmptyState))//todo david : trouver comment faire fonctionner tout ça
    }


}
