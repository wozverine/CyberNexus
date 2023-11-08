package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Category
import com.glitch.cybernexus.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val allCategoriesList = mutableListOf<Category>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onCategoryClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(allCategoriesList[position])
    }

    override fun getItemCount(): Int {
        return allCategoriesList.size
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding, val onCategoryClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            with(binding) {
                categoryTv.text  = category.title

                root.setOnClickListener{
                    onCategoryClick(category.title)
                }
            }
        }
    }

    fun updateList(list: List<Category>) {
        allCategoriesList.clear()
        allCategoriesList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}