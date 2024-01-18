package com.openclassrooms.realestatemanager.ui.addform

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddFormFragmentBinding
import com.openclassrooms.realestatemanager.ui.pictures.PicturesAdapter
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddFormFragment : Fragment(R.layout.add_form_fragment) {

    private val binding by viewBinding { AddFormFragmentBinding.bind(it) }
    private val viewModel by viewModels<AddFormViewModel>()

    companion object {
        fun newInstance() = AddFormFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PicturesAdapter()

        binding.addFormRvPictures.adapter = adapter

        val saleDate: TextInputEditText = binding.createTaskTextInputEditTextDateOfSale
        val closingSaleDate: TextInputEditText = binding.createTaskTextInputEditTextClosingDate

        viewModel.viewStateLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

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
                datePickerDialog?.datePicker?.minDate = c.timeInMillis
                datePickerDialog?.show()
        })

        closingSaleDate.setOnClickListener(View.OnClickListener { v: View? ->
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
            datePickerDialog?.datePicker?.minDate = c.timeInMillis
            datePickerDialog?.show()
        })

        val addPictureFromLibrary =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            }

        binding.buttonPhoto.setOnClickListener {
            addPictureFromLibrary.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
    }
}