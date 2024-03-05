package com.openclassrooms.realestatemanager.ui.add_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.BuildConfig
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
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
    private var maxWidth = 0


    companion object {
        fun newInstance() = AddFormFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.photo_list_fragment_container, PicturesFragment.newInstance())
            ?.commit()
        val imageContract =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                if (uris.isNotEmpty()) {
                    for (uri in uris) {
                        viewModel.addTemporaryPictureFromGallery(uri)

                    }
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        binding.buttonPhoto.setOnClickListener {
            imageContract.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        // Allows all chips to have the same width based on the widest
        val allChips = listOf(
            binding.addFormChipHouse,
            binding.addFormChipFlat,
            binding.addFormChipLoft,
            binding.addFormChipDuplex,
            binding.addFormChipLand,
            binding.addFormChipOther
        )

        allChips.forEach { chip ->
            chip.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val chipWidth = chip.measuredWidth

            if (chipWidth > maxWidth) {
                maxWidth = chipWidth
            }
            chip.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }

        allChips.forEach { chip ->
            val params = chip.layoutParams
            params.width = maxWidth
            chip.layoutParams = params
        }


        val saleDate: TextInputEditText = binding.addFormTextInputEditTextDateOfSale
        val closingSaleDate: TextInputEditText = binding.addFormTextInputEditTextClosingDate
        var minSoldDate: Long = Calendar.getInstance().timeInMillis

        if (!Places.isInitialized()) {
            context?.let { Places.initialize(it, BuildConfig.PLACES_API_KEY) }
        }

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.add_realEstate_autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS
            )
        )

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                viewModel.onAddressChanged(place.address)
            }

            override fun onError(status: Status) {
            }
        })

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                viewModel.onDateChanged(dayOfMonth, monthOfYear, year)

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                minSoldDate = calendar.timeInMillis
            }

        val soldDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                viewModel.onSoldDateChanged(dayOfMonth, monthOfYear, year)
            }

        saleDate.setOnClickListener {
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
            // est quelle est set avant la date de mise en vente il faudrait un warning

            // todo david possibilité de erase la date défini
            datePickerDialog?.show()
        }

        closingSaleDate.setOnClickListener {
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
        }

        viewModel.onSaleDateChangeLiveData.observe(viewLifecycleOwner) {
            saleDate.setText(it)
        }

        viewModel.onSoldDateChangeLiveData.observe(viewLifecycleOwner) {
            closingSaleDate.setText(it)
        }


        binding.addFormChipGroupType.setOnCheckedChangeListener { chipGroup, i ->
            getSelectedText(chipGroup, i)
            viewModel.onTypeChanged(getSelectedText(chipGroup, i))
        }

        binding.addRealEstateTextInputEditTextPrice.doAfterTextChanged {
            viewModel.onTextPriceChanged(it?.toString())
        }
        binding.addRealEstateTextInputEditTextSurface.doAfterTextChanged {
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

    }
}

private fun getSelectedText(chipGroup: ChipGroup, id: Int): String {
    val mySelection = chipGroup.findViewById<Chip>(id)
    return mySelection?.text?.toString() ?: ""
}

