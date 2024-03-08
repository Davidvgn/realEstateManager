package com.openclassrooms.realestatemanager.ui.pictures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.PictureEmptyStateBinding
import com.openclassrooms.realestatemanager.databinding.PictureItemBinding


class PicturesAdapter :
    ListAdapter<PicturesViewStateItem, PicturesAdapter.PicturesViewHolder>(PicturesDiffUtilCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PicturesViewHolder =
        when (PicturesViewStateItem.Type.values()[viewType]) {
            PicturesViewStateItem.Type.EMPTY_STATE ->
                PicturesViewHolder.EmptyState.create(parent)

            PicturesViewStateItem.Type.PICTURES -> PicturesViewHolder.Pictures.create(parent)
        }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        when (holder){
            is PicturesViewHolder.EmptyState -> Unit
            is PicturesViewHolder.Pictures -> holder.bind(picture = getItem(position) as PicturesViewStateItem.Pictures)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal


    sealed class PicturesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class EmptyState(private val binding: PictureEmptyStateBinding) :
            PicturesViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = EmptyState(
                    binding = PictureEmptyStateBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }

        class Pictures(private val binding: PictureItemBinding) : PicturesViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = Pictures(
                    binding = PictureItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            fun bind(picture: PicturesViewStateItem.Pictures) {
                Glide
                    .with(itemView.context)
                    .load(picture.uri)
                    .into(binding.pictureImageView)

                binding.pictureDescription.setText(picture.title)
            }
        }
    }


    object PicturesDiffUtilCallback : DiffUtil.ItemCallback<PicturesViewStateItem>() {
        override fun areItemsTheSame(
            oldItem: PicturesViewStateItem,
            newItem: PicturesViewStateItem
        ): Boolean = when {
            oldItem is PicturesViewStateItem.EmptyState && newItem is PicturesViewStateItem.EmptyState -> true
            oldItem is PicturesViewStateItem.Pictures && newItem is PicturesViewStateItem.Pictures -> oldItem.id == newItem.id
            else -> false
        }

        override fun areContentsTheSame(
            oldItem: PicturesViewStateItem,
            newItem: PicturesViewStateItem
        ): Boolean =
            oldItem == newItem
    }
}


