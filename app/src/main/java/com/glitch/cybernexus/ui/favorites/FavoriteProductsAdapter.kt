package com.glitch.cybernexus.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.response.Product
import com.glitch.cybernexus.databinding.ItemProductHomeBinding

class FavoriteProductsAdapter(
    private val onFavoriteProductClick: (String) -> Unit
) : RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteProductsViewHolder>() {
    private val allFavoriteProductsList = mutableListOf<Product>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FavoriteProductsViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductsViewHolder(binding, onFavoriteProductClick)
    }

    override fun onBindViewHolder(
        holder: FavoriteProductsViewHolder, position: Int
    ) {
        holder.bind(allFavoriteProductsList[position])
    }

    override fun getItemCount(): Int {
        return allFavoriteProductsList.size
    }

    class FavoriteProductsViewHolder(
        private val binding: ItemProductHomeBinding, val onFavoriteProductClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                val strList = product.title?.split(" ")

                tvProductName.text = product.title
                tvCategory.text = product.category

                root.setOnClickListener {
                    product.title?.let { it1 -> onFavoriteProductClick(it1) }
                }
            }
        }
    }

    fun updateList(list: List<Product>) {
        allFavoriteProductsList.clear()
        allFavoriteProductsList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}