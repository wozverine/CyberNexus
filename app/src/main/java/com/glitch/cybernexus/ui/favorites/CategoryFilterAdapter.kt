package com.glitch.cybernexus.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.databinding.ItemCategoryBinding

class CategoryFilterAdapter(
    private val onCategoryFilterClick: (String) -> Unit
) : ListAdapter<String, CategoryFilterAdapter.CategoryViewHolder>(CategoryFilterAdapter.CategoryFilterDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onCategoryFilterClick
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))


    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val onCategoryFilterClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            with(binding) {
                categoryTv.text = category

                root.setOnClickListener {
                    onCategoryFilterClick(category)
                }
            }
        }
    }

    class CategoryFilterDiffUtilCallBack : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}