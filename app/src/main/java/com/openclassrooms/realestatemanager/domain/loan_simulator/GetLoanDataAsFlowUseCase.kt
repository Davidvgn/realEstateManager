package com.openclassrooms.realestatemanager.domain.loan_simulator


import com.openclassrooms.realestatemanager.domain.loan_simulator.model.LoanDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoanDataAsFlowUseCase @Inject constructor(
    private val loanSimulatorRepository: LoanSimulatorRepository,
) {
    fun invoke(): Flow<LoanDataEntity> = loanSimulatorRepository.getLoanDataAsFlow()
}