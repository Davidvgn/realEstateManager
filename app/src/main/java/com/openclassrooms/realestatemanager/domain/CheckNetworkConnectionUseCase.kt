package com.openclassrooms.realestatemanager.domain

import javax.inject.Inject

class CheckNetworkConnectionUseCase
    @Inject
    constructor(
        private val networkRepository: NetworkRepository,
    ) {
        suspend fun invoke() = networkRepository.isNetworkAvailable()
    }
