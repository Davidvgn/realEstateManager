package com.openclassrooms.realestatemanager.ui.addform

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddFormFragmentBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddFormFragment : Fragment(R.layout.add_form_fragment) {

    private val binding by viewBinding { AddFormFragmentBinding.bind(it) }


    companion object {
        fun newInstance() = AddFormFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.card?.setOnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_LONG).show()

        }

        val saleDate: TextInputEditText = binding.createTaskTextInputEditTextListingDate

        saleDate.setInputType(InputType.TYPE_NULL)
        saleDate.setOnClickListener(View.OnClickListener { v: View? ->
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR] // current year
            val month = c[Calendar.MONTH] // current month
            val day = c[Calendar.DAY_OF_MONTH] // current day
            val datePickerDialog = context?.let {
                DatePickerDialog(
                    it,
                    { view: DatePicker?, selectedYear: Int, selectedMonthOfYear: Int, selectedDayOfMonth: Int ->
        //                    viewModel.onDateChanged(
        //                        selectedDayOfMonth,
        //                        selectedMonthOfYear,
        //                        selectedYear
        //                    )
                    },
                    year,
                    month,
                    day
                )
            }
            if (datePickerDialog != null) {
                datePickerDialog.datePicker.minDate = c.timeInMillis
            }
            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        })


    }
}