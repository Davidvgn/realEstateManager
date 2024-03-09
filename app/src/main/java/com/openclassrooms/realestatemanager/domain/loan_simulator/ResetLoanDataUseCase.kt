package com.openclassrooms.realestatemanager.domain.loan_simulator

import com.openclassrooms.realestatemanager.domain.loan_simulator.model.LoanDataEntity
import javax.inject.Inject

class ResetLoanDataUseCase @Inject constructor(
    private val loanSimulatorRepository: LoanSimulatorRepository,
) {
    fun invoke() = loanSimulatorRepository.setLoanData(LoanDataEntity())
}