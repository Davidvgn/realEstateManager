package com.openclassrooms.realestatemanager.ui.real_estates

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateItemBinding

class RealEstatesAdapter :
    ListAdapter<RealEstatesViewSateItem, RealEstatesAdapter.RealEstatesViewHolder>(RealEstatesDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstatesViewHolder = RealEstatesViewHolder(
        RealEstateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: RealEstatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class RealEstatesViewHolder(private val binding: RealEstateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(realEstate: RealEstatesViewSateItem){
                val resourceId: Int = R.drawable.sample_image
                val uri: Uri = Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId")
                binding.realEstateItemTextViewType.text = realEstate.type
                binding.realEstateItemTextViewCity.text = realEstate.city
                binding.realEstateItemTextViewPrice.text = realEstate.salePrice.toString()

                Glide.with(binding.realEstateItemImageView.context)
                    .load(uri)
                    .into(binding.realEstateItemImageView)
            }

    }
    object RealEstatesDiffUtilCallback : DiffUtil.ItemCallback<RealEstatesViewSateItem>() {
        override fun areItemsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem
        ): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem
        ): Boolean = oldItem == newItem
    }

}