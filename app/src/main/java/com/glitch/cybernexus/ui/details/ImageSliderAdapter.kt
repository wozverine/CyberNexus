package com.glitch.cybernexus.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.databinding.ImageSliderBinding

class ImageSliderAdapter : RecyclerView.Adapter<ImageSliderAdapter.PageViewHolder>() {

    private val imageList = ArrayList<String>()

    class PageViewHolder(val binding: ImageSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            with(binding) {
                Glide.with(ivSlide).load(imageUrl).into(ivSlide)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val binding = ImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        imageList[position].let {
            holder.bind(it)
        }
    }

    fun updateList(list: List<String>) {
        imageList.clear()
        imageList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}