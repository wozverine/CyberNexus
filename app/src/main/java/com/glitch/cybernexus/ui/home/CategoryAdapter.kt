package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (String) -> Unit
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(CategoryAdapter.CategoryDiffUtilCallBack()) {
//) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    //private val allCategoriesList = mutableListOf<Category>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryAdapter.CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onCategoryClick
        )/*val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onCategoryClick)*/
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))


    /*override fun getItemCount(): Int {
        return allCategoriesList.size
    }*/

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding, private val onCategoryClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            with(binding) {
                categoryTv.text = category

                root.setOnClickListener {
                    onCategoryClick(category)
                }
            }
        }
    }

    class CategoryDiffUtilCallBack : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}