package com.example.gallery

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gallery.data.PhotoItem
import kotlinx.android.synthetic.main.pager_photo_view.view.*

class PhotoAdapter : ListAdapter<PhotoItem, PhotoAdapter.VM>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VM {
        LayoutInflater.from(parent.context).inflate(R.layout.pager_photo_view, parent, false)
            .apply {
                return VM(this)
            }
    }

    override fun onBindViewHolder(holder: VM, position: Int) {
        holder.itemView.shimmerlayoutCell.apply {
            setShimmerColor(0x55ffffff)
            setShimmerAngle(0)
            startShimmerAnimation()
        }

        Glide.with(holder.itemView)
            .load(getItem(position).previewUrl)
            .placeholder(R.drawable.ic_image_black_24dp)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also { holder.itemView.shimmerlayoutCell?.stopShimmerAnimation() }
                }
            })
            .into(holder.itemView.pagerPhoto)
    }

    class VM(itemView: View) : RecyclerView.ViewHolder(itemView)
}
