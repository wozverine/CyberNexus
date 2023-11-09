package com.glitch.cybernexus.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Category
import com.glitch.cybernexus.databinding.ItemCategoryBinding

class CategoryFilterAdapter(
    private val onCategoryFilterClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryFilterAdapter.CategoryViewHolder>() {

    private val categoryFilterList = mutableListOf<Category>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CategoryFilterAdapter.CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onCategoryFilterClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryFilterList[position])
    }

    override fun getItemCount(): Int {
        return categoryFilterList.size
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding, val onCategoryFilterClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            with(binding) {
                categoryTv.text = category.title

                root.setOnClickListener {
                    onCategoryFilterClick(category.title)
                }
            }
        }
    }

    fun updateList(list: List<Category>) {
        categoryFilterList.clear()
        categoryFilterList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}