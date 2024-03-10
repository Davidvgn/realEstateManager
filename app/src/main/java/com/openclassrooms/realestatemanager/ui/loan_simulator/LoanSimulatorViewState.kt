package com.openclassrooms.realestatemanager.ui.loan_simulator

import com.openclassrooms.realestatemanager.ui.utils.EquatableCallback
import com.openclassrooms.realestatemanager.ui.utils.NativeText

data class LoanSimulatorViewState(
    val loanAmount: String,
    val loanRate: String,
    val loanDuration: String,
    val loanCurrencyHint: NativeText,
    val yearlyAndMonthlyPayment: NativeText?,
    val onCalculateClicked: EquatableCallback,
    val onResetClicked: EquatableCallback,
)
