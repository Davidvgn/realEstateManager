package com.openclassrooms.realestatemanager.domain.loan_simulator.model

import java.math.BigDecimal

data class LoanParamsEntity(
    val loanAmount: BigDecimal = BigDecimal.ZERO,
    val interestRate: BigDecimal = BigDecimal.ZERO,
    val loanDuration: BigDecimal = BigDecimal.ZERO,
    val yearlyPayment: BigDecimal = BigDecimal.ZERO,
    val monthlyPayment: BigDecimal = BigDecimal.ZERO,
)
