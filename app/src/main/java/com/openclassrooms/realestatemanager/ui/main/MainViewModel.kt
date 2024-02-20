package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.domain.permission.RefreshPermissionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val refreshPermissionsUseCase: RefreshPermissionsUseCase,
    ): ViewModel(){
    fun onPermissionUpdated() {
        refreshPermissionsUseCase.invoke()
    }
}