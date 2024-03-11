package com.openclassrooms.realestatemanager.ui.pictures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.PictureEmptyStateBinding
import com.openclassrooms.realestatemanager.ui.ImageTextView
import com.openclassrooms.realestatemanager.ui.OnPictureClickedListener

class PicturesAdapter(
    private val listener: OnPictureClickedListener,
) :
    ListAdapter<PicturesViewStateItem, PicturesAdapter.PicturesViewHolder>(PicturesDiffUtilCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PicturesViewHolder =
        when (PicturesViewStateItem.Type.values()[viewType]) {
            PicturesViewStateItem.Type.EMPTY_STATE ->
                PicturesViewHolder.EmptyState.create(parent)

            PicturesViewStateItem.Type.PICTURES ->
                PicturesViewHolder.Pictures.create(
                    parent,
                    listener,
                )
        }

    override fun onBindViewHolder(
        holder: PicturesViewHolder,
        position: Int,
    ) {
        when (holder) {
            is PicturesViewHolder.EmptyState -> Unit
            is PicturesViewHolder.Pictures ->
                holder.bind(
                    picture = getItem(position) as PicturesViewStateItem.Pictures,
                    listener,
                )
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    sealed class PicturesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class EmptyState(private val binding: PictureEmptyStateBinding) :
            PicturesViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) =
                    EmptyState(
                        binding =
                            PictureEmptyStateBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false,
                            ),
                    )
            }
        }

        class Pictures(private val imageTextView: ImageTextView) :
            PicturesViewHolder(imageTextView) {
            companion object {
                fun create(
                    parent: ViewGroup,
                    listener: OnPictureClickedListener,
                ): Pictures {
                    val imageTextView = ImageTextView(parent.context)
                    return Pictures(imageTextView)
                }
            }

            fun bind(
                picture: PicturesViewStateItem.Pictures,
                listener: OnPictureClickedListener,
            ) {
                imageTextView.setImageResource(picture.uri.toUri())
                imageTextView.setText(picture.title)

                imageTextView.findViewById<ImageView>(R.id.custom_imageView_image)
                    .setOnClickListener {
                        listener.onPictureClickedListener(picture.uri.toUri(), picture.title)
                    }
            }
        }
    }

    object PicturesDiffUtilCallback : DiffUtil.ItemCallback<PicturesViewStateItem>() {
        override fun areItemsTheSame(
            oldItem: PicturesViewStateItem,
            newItem: PicturesViewStateItem,
        ): Boolean =
            when {
                oldItem is PicturesViewStateItem.EmptyState && newItem is PicturesViewStateItem.EmptyState -> true
                oldItem is PicturesViewStateItem.Pictures && newItem is PicturesViewStateItem.Pictures -> oldItem.id == newItem.id
                else -> false
            }

        override fun areContentsTheSame(
            oldItem: PicturesViewStateItem,
            newItem: PicturesViewStateItem,
        ): Boolean = oldItem == newItem
    }
}
