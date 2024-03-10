package com.openclassrooms.realestatemanager.domain.loan_simulator

import com.openclassrooms.realestatemanager.domain.loan_simulator.model.LoanDataEntity
import kotlinx.coroutines.flow.Flow

interface LoanSimulatorRepository {
    fun setLoanData(loanDataEntity: LoanDataEntity)

    fun getLoanDataAsFlow(): Flow<LoanDataEntity>

    fun resetLoanData()
}
