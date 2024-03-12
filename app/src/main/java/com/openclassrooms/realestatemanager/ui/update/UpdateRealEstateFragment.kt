package com.openclassrooms.realestatemanager.ui.update

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.UpdateRealEstateFragmentBinding
import com.openclassrooms.realestatemanager.ui.PictureDescriptionListener
import com.openclassrooms.realestatemanager.ui.add_form.AddPictureDescriptionDialog
import com.openclassrooms.realestatemanager.ui.add_form.AgentSpinnerAdapter
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.Calendar

@AndroidEntryPoint
class UpdateRealEstateFragment :
    Fragment(R.layout.update_real_estate_fragment),
    PictureDescriptionListener {
    private val binding by viewBinding { UpdateRealEstateFragmentBinding.bind(it) }
    private val viewModel by viewModels<UpdateRealEstateViewModel>()
    private var maxWidth = 0
    private lateinit var cameraPhotoUri: Uri

    companion object {
        const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"

        fun newInstance(realEstateId: Long): UpdateRealEstateFragment {
            val fragment = UpdateRealEstateFragment()
            val args = Bundle()
            args.putLong(KEY_REAL_ESTATE_ID, realEstateId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val realEstateId = arguments?.getLong(KEY_REAL_ESTATE_ID, -1) ?: -1

        viewModel.initRealEstateId(id = realEstateId)

        val adapter = AgentSpinnerAdapter()
        binding.updateRealEstateTextInputEditTextRealEstateAgent.setOnItemClickListener { _, _, position, _ ->
            adapter.getItem(position)?.let {
                viewModel.onAgentSelected(it.name)
            }
        }

        viewModel.fetchAgents()

        viewModel.agentsLiveData.observe(viewLifecycleOwner) { agents ->
            adapter.setData(agents)
            binding.updateRealEstateTextInputEditTextRealEstateAgent.setAdapter(adapter)
        }

        val saleDate: TextInputEditText = binding.updateRealEstateTextInputEditTextDateOfSale
        val closingSaleDate: TextInputEditText =
            binding.updateRealEstateTextInputEditTextClosingDate
        var minSoldDate: Long = Calendar.getInstance().timeInMillis
        val allChips =
            listOf(
                binding.updateRealEstateChipHouse,
                binding.updateRealEstateChipFlat,
                binding.updateRealEstateChipLoft,
                binding.updateRealEstateChipDuplex,
                binding.updateRealEstateChipLand,
                binding.updateRealEstateChipOther,
            )

//        activity?.supportFragmentManager?.beginTransaction()
//            ?.replace(R.id.photo_list_fragment_container, PicturesFragment.newInstance())
//            ?.commit()

        // Pictures from gallery
        val imageContract =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    AddPictureDescriptionDialog.newInstance(uri, this)
                        .show(childFragmentManager, null)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        binding.updateRealEstateButtonPhotoFromGallery.setOnClickListener {
            imageContract.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Pictures from camera
        binding.updateRealEstateButtonPhotoFromCamera.setOnClickListener {
            cameraPhotoUri =
                FileProvider.getUriForFile(
                    requireContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    File.createTempFile(
                        "JPEG_",
                        ".jpg",
                        requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    ),
                )

            val intent =
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    .putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri)

            @Suppress("DEPRECATION")
            startActivityForResult(intent, 0)
        }

        binding.updateRealEstateTvSelectedAddress.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        // Allows all chips to have the same width based on the widest
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

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.update_real_estate_autocomplete_fragment)
                as AutocompleteSupportFragment

        if (!Places.isInitialized()) {
            context?.let { Places.initialize(it, BuildConfig.PLACES_API_KEY) }
        }
        // Autocomplete address field
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
            ),
        )

        autocompleteFragment.setOnPlaceSelectedListener(
            object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    binding.updateRealEstateTvSelectedAddress.text = place.address?.toString()

                    viewModel.onAddressChanged(place.address?.toString())

                    val placeLatLng: LatLng? = place.latLng
                    if (placeLatLng != null) {
                        viewModel.addLatLng(placeLatLng.latitude, placeLatLng.longitude)
                    }
                }

                override fun onError(status: Status) {
                }
            },
        )

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
            val datePickerDialog =
                context?.let {
                    DatePickerDialog(
                        it,
                        dateSetListener,
                        year,
                        month,
                        day,
                    )
                }

            datePickerDialog?.datePicker?.minDate = c.timeInMillis
            // todo david amélioration : Si la date de vente a été sélectionnée (par mégarde) avant la date de mise en vente,
            // est quelle est set avant la date de mise en vente il faudrait un warning

            // todo david possibilité de erase la date défini
            datePickerDialog?.show()
        }

        closingSaleDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR] // current year
            val month = c[Calendar.MONTH] // current month
            val day = c[Calendar.DAY_OF_MONTH] // current day
            val datePickerDialog =
                context?.let {
                    DatePickerDialog(
                        it,
                        soldDateSetListener,
                        year,
                        month,
                        day,
                    )
                }
            datePickerDialog?.datePicker?.minDate = minSoldDate
            datePickerDialog?.show()
        }

        viewModel.upForSaleDateChangeLiveData.observe(viewLifecycleOwner) {
            saleDate.setText(it)
        }

        viewModel.onSoldDateChangeLiveData.observe(viewLifecycleOwner) {
            closingSaleDate.setText(it)
        }

        binding.updateRealEstateChipGroupType.setOnCheckedChangeListener { chipGroup, i ->
            getSelectedText(chipGroup, i)
            viewModel.onTypeChanged(
                getSelectedText(
                    chipGroup,
                    i,
                ),
            )
        }

        binding.updateRealEstateCheckBoxPharmacie.setOnClickListener {
            viewModel.addPoi(
                binding.updateRealEstateCheckBoxPharmacie.text.toString(),
                binding.updateRealEstateCheckBoxPharmacie.isChecked,
            )
        }

        binding.updateRealEstateCheckBoxHospitals.setOnClickListener {
            viewModel.addPoi(
                binding.updateRealEstateCheckBoxHospitals.text.toString(),
                binding.updateRealEstateCheckBoxHospitals.isChecked,
            )
        }
        binding.updateRealEstateCheckBoxRestaurant.setOnClickListener {
            viewModel.addPoi(
                binding.updateRealEstateCheckBoxRestaurant.text.toString(),
                binding.updateRealEstateCheckBoxRestaurant.isChecked,
            )
        }
        binding.updateRealEstateCheckBoxSchool.setOnClickListener {
            viewModel.addPoi(
                binding.updateRealEstateCheckBoxSchool.text.toString(),
                binding.updateRealEstateCheckBoxSchool.isChecked,
            )
        }
        binding.updateRealEstateCheckBoxShops.setOnClickListener {
            viewModel.addPoi(
                binding.updateRealEstateCheckBoxShops.text.toString(),
                binding.updateRealEstateCheckBoxShops.isChecked,
            )
        }
        binding.updateRealEstateCheckBoxTransportation.setOnClickListener {
            viewModel.addPoi(
                binding.updateRealEstateCheckBoxTransportation.text.toString(),
                binding.updateRealEstateCheckBoxTransportation.isChecked,
            )
        }
        binding.updateRealEstateRadioButtonSold.setOnClickListener {
            if (binding.updateRealEstateRadioButtonSold.isChecked) {
                viewModel.onStatusSelected(binding.updateRealEstateRadioButtonSold.text.toString())
            }
        }
        binding.updateRealEstateRadioButtonToSale.setOnClickListener {
            if (binding.updateRealEstateRadioButtonToSale.isChecked) {
                viewModel.onStatusSelected(binding.updateRealEstateRadioButtonToSale.text.toString())
            }
        }

        binding.updateRealEstateTextInputEditTextPrice.doAfterTextChanged {
            viewModel.onTextPriceChanged(it?.toString())
        }

        binding.updateRealEstateTextViewTitleNumberOfRooms.doAfterTextChanged {
            viewModel.onNumberOfRoomsChanged(it?.toString())
        }
        binding.updateRealEstateTextInputEditTextSurface.doAfterTextChanged {
            viewModel.onTextFloorAreaChanged(it?.toString())
        }

        binding.updateRealEstateTextInputEditTextDescription.doAfterTextChanged {
            viewModel.onTextDescriptionChanged(it?.toString())
        }

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { updateViewState ->
            for (chip in binding.updateRealEstateChipGroupType.children) {
                if (chip is Chip) {
                    chip.isChecked = (chip.text == updateViewState.type)
                }
            }

            if (updateViewState.status == "Sold") { // todo david hardcoded text
                binding.updateRealEstateRadioButtonSold.isChecked = true
            }

            val valueToCheck = updateViewState.numberOfRooms

            for (i in 0 until binding.updateRealEstateRadioGroup.childCount) {
                val radioButton = binding.updateRealEstateRadioGroup.getChildAt(i) as RadioButton
                if (radioButton.text == valueToCheck) {
                    radioButton.isChecked = true
                    break
                }
            }

// todo david hardcoded text + wrong way to do it
            for (poi in updateViewState.poi) {
                if (poi == "School") {
                    viewModel.addPoi(poi, true)
                    binding.updateRealEstateCheckBoxSchool.isChecked = true
                }
                if (poi == "Transportation") {
                    viewModel.addPoi(poi, true)
                    binding.updateRealEstateCheckBoxTransportation.isChecked = true
                }
                if (poi == "Restaurants") {
                    viewModel.addPoi(poi, true)
                    binding.updateRealEstateCheckBoxRestaurant.isChecked = true
                }
                if (poi == "Hospitals") {
                    viewModel.addPoi(poi, true)
                    binding.updateRealEstateCheckBoxHospitals.isChecked = true
                }
                if (poi == "Pharmacy") {
                    viewModel.addPoi(poi, true)
                    binding.updateRealEstateCheckBoxPharmacie.isChecked = true
                }
                if (poi == "Shops") {
                    viewModel.addPoi(poi, true)
                    binding.updateRealEstateCheckBoxShops.isChecked = true
                }
            }

            binding.updateRealEstateTextInputEditTextPrice.setText(updateViewState.salePrice)
            binding.updateRealEstateTextInputEditTextDescription.setText(updateViewState.description)
            binding.updateRealEstateTvSelectedAddress.text = (updateViewState.address)
        }

        binding.updateRealEstateButtonUpdate.setOnClickListener {
            viewModel.updateViewStateLiveData.observe(viewLifecycleOwner) {
                binding.updateRealEstateTextInputEditTextDescription.setText(it.description)
                binding.updateRealEstateTextInputEditTextPrice.setText(it.salePrice)
                binding.updateRealEstateTextInputEditTextDateOfSale.setText(it.dateOfSale)
                binding.updateRealEstateTextInputEditTextSurface.setText(it.floorArea)
                binding.updateRealEstateTvSelectedAddress.text = (it.address)
            }
            activity?.finish()
        }

        viewModel.showToastSingleLiveEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            AddPictureDescriptionDialog.newInstance(cameraPhotoUri, this)
                .show(childFragmentManager, null)
        }
    }

    override fun onDescriptionFilled(
        uri: Uri,
        title: String,
    ) {
        if (title != "") {
            viewModel.addTemporaryPicture(uri, title)
        }
    }
}

private fun getSelectedText(
    chipGroup: ChipGroup,
    id: Int,
): String {
    val mySelection = chipGroup.findViewById<Chip>(id)
    return mySelection?.text?.toString() ?: ""
}
