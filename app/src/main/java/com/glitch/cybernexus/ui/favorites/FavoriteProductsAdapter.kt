package com.glitch.cybernexus.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Product
import com.glitch.cybernexus.databinding.ItemProductHomeBinding

class FavoriteProductsAdapter(
    private val onFavoriteProductClick: (String) -> Unit
) : RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteProductsViewHolder>() {
    private val allFavoriteProductsList = mutableListOf<Product>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FavoriteProductsAdapter.FavoriteProductsViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductsViewHolder(binding, onFavoriteProductClick)
    }

    override fun onBindViewHolder(
        holder: FavoriteProductsAdapter.FavoriteProductsViewHolder, position: Int
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
                tvProductName.text = product.title
                tvCompany.text = product.company

                root.setOnClickListener {
                    onFavoriteProductClick(product.company)
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