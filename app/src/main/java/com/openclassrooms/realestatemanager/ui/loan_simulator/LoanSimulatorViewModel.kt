package com.openclassrooms.realestatemanager.ui.loan_simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatRoundedPriceToHumanReadable
import com.openclassrooms.realestatemanager.domain.currency.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.loan_simulator.GetLoanDataAsFlowUseCase
import com.openclassrooms.realestatemanager.domain.loan_simulator.GetLoanYearlyAndMonthlyPaymentUseCase
import com.openclassrooms.realestatemanager.domain.loan_simulator.ResetLoanDataUseCase
import com.openclassrooms.realestatemanager.domain.loan_simulator.SetLoanDataUseCase
import com.openclassrooms.realestatemanager.domain.loan_simulator.model.LoanParamsEntity
import com.openclassrooms.realestatemanager.ui.utils.EquatableCallback
import com.openclassrooms.realestatemanager.ui.utils.Event
import com.openclassrooms.realestatemanager.ui.utils.NativeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class LoanSimulatorViewModel
    @Inject
    constructor(
        private val setLoanDataUseCase: SetLoanDataUseCase,
        private val getLoanDataAsFlowUseCase: GetLoanDataAsFlowUseCase,
        private val getLoanYearlyAndMonthlyPaymentUseCase: GetLoanYearlyAndMonthlyPaymentUseCase,
        private val getCurrencyTypeUseCase: GetCurrentCurrencyUseCase,
        private val resetLoanDataUseCase: ResetLoanDataUseCase,
    ) : ViewModel() {
        private val loanParamsEntityMutableStateFlow: MutableStateFlow<LoanParamsEntity> =
            MutableStateFlow(LoanParamsEntity())
        private val errorMessageMutableSharedFlow: MutableStateFlow<LoanErrorMessages> =
            MutableStateFlow(LoanErrorMessages())

        val viewState: LiveData<LoanSimulatorViewState> =
            liveData {
                coroutineScope {
                    launch {
                        getLoanDataAsFlowUseCase.invoke().collect { loanData ->
                            loanParamsEntityMutableStateFlow.update {
                                it.copy(
                                    loanAmount = loanData.loanAmount,
                                    interestRate = loanData.interestRate,
                                    loanDuration = loanData.loanDuration,
                                    yearlyPayment = loanData.yearlyPayment,
                                    monthlyPayment = loanData.monthlyPayment,
                                )
                            }
                        }
                    }

                    setLoanDataUseCase.invoke(loanParamsEntityMutableStateFlow.value)

                    loanParamsEntityMutableStateFlow.collectLatest { loanParams ->
                        emit(
                            LoanSimulatorViewState(
                                loanAmount = if (loanParams.loanAmount == BigDecimal.ZERO) "" else loanParams.loanAmount.toString(),
                                loanRate = if (loanParams.interestRate == BigDecimal.ZERO) "" else loanParams.interestRate.toString(),
                                loanDuration = if (loanParams.loanDuration == BigDecimal.ZERO) "" else loanParams.loanDuration.toString(),
                                loanCurrencyHint =
                                    NativeText.Argument(
                                        R.string.loan_amount_hint,
                                        getCurrencyTypeUseCase.invoke(),
                                    ),
                                yearlyAndMonthlyPayment =
                                    if (
                                        loanParams.yearlyPayment == BigDecimal.ZERO &&
                                        loanParams.monthlyPayment == BigDecimal.ZERO
                                    ) {
                                        null
                                    } else {
                                        NativeText.Arguments(
                                            R.string.loan_simulator_payment_result,
                                            listOf(
                                                formatRoundedPriceToHumanReadable(loanParams.yearlyPayment),
                                                formatRoundedPriceToHumanReadable(loanParams.monthlyPayment),
                                            ),
                                        )
                                    },
                                onCalculateClicked =
                                    EquatableCallback {
                                        if (loanParams.loanAmount == BigDecimal.ZERO ||
                                            loanParams.interestRate == BigDecimal.ZERO ||
                                            loanParams.loanDuration == BigDecimal.ZERO
                                        ) {
                                            errorMessageMutableSharedFlow.tryEmit(
                                                LoanErrorMessages(
                                                    amountErrorMessage =
                                                        if (loanParams.loanAmount == BigDecimal.ZERO) {
                                                            NativeText.Resource(
                                                                R.string.loan_simulator_calculate_button_error_message,
                                                            )
                                                        } else {
                                                            null
                                                        },
                                                    interestRateErrorMessage =
                                                        if (loanParams.interestRate == BigDecimal.ZERO) {
                                                            NativeText.Resource(
                                                                R.string.loan_simulator_calculate_button_error_message,
                                                            )
                                                        } else {
                                                            null
                                                        },
                                                    loanDurationErrorMessage =
                                                        if (loanParams.loanDuration == BigDecimal.ZERO) {
                                                            NativeText.Resource(
                                                                R.string.loan_simulator_calculate_button_error_message,
                                                            )
                                                        } else {
                                                            null
                                                        },
                                                ),
                                            )
                                            return@EquatableCallback
                                        }
                                        val result =
                                            getLoanYearlyAndMonthlyPaymentUseCase.invoke(
                                                loanParamsEntityMutableStateFlow.value.loanAmount,
                                                loanParamsEntityMutableStateFlow.value.interestRate,
                                                loanParamsEntityMutableStateFlow.value.loanDuration,
                                            )
                                        if (result != null) {
                                            setLoanDataUseCase.invoke(loanParamsEntityMutableStateFlow.value)
                                            loanParamsEntityMutableStateFlow.update {
                                                it.copy(
                                                    yearlyPayment = result.yearlyPayment,
                                                    monthlyPayment = result.monthlyPayment,
                                                )
                                            }
                                        }
                                    },
                                onResetClicked =
                                    EquatableCallback {
                                        resetLoanDataUseCase.invoke()
                                    },
                            ),
                        )
                    }
                }
            }

        val viewEvent: LiveData<Event<LoanErrorMessages>> =
            liveData {
                errorMessageMutableSharedFlow.collectLatest {
                    emit(Event(it))
                }
            }

        fun onLoanAmountChanged(loanAmount: String) {
            if (errorMessageMutableSharedFlow.value.amountErrorMessage != null) {
                errorMessageMutableSharedFlow.tryEmit(LoanErrorMessages(amountErrorMessage = null))
            }

            if (loanAmount.isBlank() || loanAmount.startsWith("0")) return

            loanParamsEntityMutableStateFlow.update {
                it.copy(
                    loanAmount = BigDecimal(loanAmount),
                )
            }
        }

        fun onInterestRateChanged(interestRate: String) {
            if (errorMessageMutableSharedFlow.value.interestRateErrorMessage != null) {
                errorMessageMutableSharedFlow.tryEmit(LoanErrorMessages(interestRateErrorMessage = null))
            }
            if (interestRate.isBlank() || interestRate.startsWith("0")) return

            loanParamsEntityMutableStateFlow.update {
                it.copy(
                    interestRate = BigDecimal(interestRate),
                )
            }
        }

        fun onLoanDurationChanged(loanDuration: String) {
            if (errorMessageMutableSharedFlow.value.loanDurationErrorMessage != null) {
                errorMessageMutableSharedFlow.tryEmit(LoanErrorMessages(loanDurationErrorMessage = null))
            }
            if (loanDuration.isBlank() || loanDuration.startsWith("0")) return

            loanParamsEntityMutableStateFlow.update {
                it.copy(
                    loanDuration = BigDecimal(loanDuration),
                )
            }
        }

        fun onResume() {
            setLoanDataUseCase.invoke(loanParamsEntityMutableStateFlow.value)
        }

        fun onLoanAmountReset() {
            loanParamsEntityMutableStateFlow.update {
                it.copy(
                    interestRate = BigDecimal.ZERO,
                )
            }
        }

        fun onInterestRateReset() {
            loanParamsEntityMutableStateFlow.update {
                it.copy(
                    interestRate = BigDecimal.ZERO,
                )
            }
        }

        fun onLoanDurationReset() {
            loanParamsEntityMutableStateFlow.update {
                it.copy(
                    loanDuration = BigDecimal.ZERO,
                )
            }
        }

        fun onStop() {
            resetLoanDataUseCase.invoke()
        }
    }

data class LoanErrorMessages(
    val amountErrorMessage: NativeText? = null,
    val interestRateErrorMessage: NativeText? = null,
    val loanDurationErrorMessage: NativeText? = null,
)
