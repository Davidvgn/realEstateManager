package com.openclassrooms.realestatemanager.ui.addform

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddFormFragmentBinding
import com.openclassrooms.realestatemanager.ui.pictures.PicturesFragment
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

        activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.photo_list_fragment_container, PicturesFragment.newInstance())
                ?.commit()

        val saleDate: TextInputEditText = binding.createTaskTextInputEditTextDateOfSale
        val closingSaleDate: TextInputEditText = binding.createTaskTextInputEditTextClosingDate
        var minSoldDate: Long = Calendar.getInstance().timeInMillis

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, i ->
            getSelectedText(chipGroup, i)
            viewModel.onTypeChanged(getSelectedText(chipGroup, i))
        }

        binding.addRealEstateTextInputEditTextPrice.doAfterTextChanged {
            viewModel.onTextPriceChanged(it?.toString())
        }
        binding.addRealEstateTextInputEditTextDescription.doAfterTextChanged {
            viewModel.onTextFloorAreaChanged(it?.toString())
        }

        binding.addRealEstateTextInputEditTextDescription.doAfterTextChanged {
            viewModel.onTextDescriptionChanged(it?.toString())
        }

        binding.addbutton.setOnClickListener {
            viewModel.viewStateAddRealEstateLiveData.observe(viewLifecycleOwner) {
            }
            activity?.finish()
        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val day = formatDayOfMonth(dayOfMonth)
            val month = formatMonth(monthOfYear)

            saleDate.setText(getString(R.string.date, day, month, year.toString()))
            viewModel.onDateChanged(
                    day, month, year.toString()
            )
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            minSoldDate = calendar.timeInMillis
        }

        val soldDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val day = formatDayOfMonth(dayOfMonth)
            val month = formatMonth(monthOfYear)

            closingSaleDate.setText(getString(R.string.date, day, month, year.toString()))
            viewModel.onSoldDateChanged(
                    day, month, year.toString()
            )
        }

        saleDate.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR] // current year
            val month = c[Calendar.MONTH] // current month
            val day = c[Calendar.DAY_OF_MONTH] // current day
            val datePickerDialog = context?.let {
                DatePickerDialog(
                        it, dateSetListener,
                        year,
                        month,
                        day
                )
            }

            datePickerDialog?.datePicker?.minDate = c.timeInMillis
            //todo david amélioration : Si la date de vente a été sélectionnée (par mégarde) avant la date de mise en vente,
            // la date de vente n'est pas MAJ, elle devrait revenir à null
            // gérer ça dans le VM
            // De plus, il faudrait créer un bouton pour érase la date de vente, car si sélectionnée par erreur on ne peut plus l'enlever
            datePickerDialog?.show()
        })

        closingSaleDate.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR] // current year
            val month = c[Calendar.MONTH] // current month
            val day = c[Calendar.DAY_OF_MONTH] // current day
            val datePickerDialog = context?.let {
                DatePickerDialog(
                        it, soldDateSetListener,
                        year,
                        month,
                        day
                )
            }
            datePickerDialog?.datePicker?.minDate = minSoldDate
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

private fun getSelectedText(chipGroup: ChipGroup, id: Int): String {
    val mySelection = chipGroup.findViewById<Chip>(id)
    return mySelection?.text?.toString() ?: ""
}

private fun formatDayOfMonth(dayOfMonth: Int) =
        if (dayOfMonth < 10) {
            "0$dayOfMonth"
        } else {
            "$dayOfMonth"
        }

private fun formatMonth(month: Int) =
        if ((month + 1) < 10) {
            "0${month + 1}"
        } else {
            "${month + 1}"
        }