package com.glitch.cybernexus.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.ItemProductHomeBinding

class FavoriteProductsAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onDeleteClick: (ProductUI) -> Unit
) : ListAdapter<ProductUI, FavoriteProductsAdapter.FavoriteProductsViewHolder>(FavProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FavoriteProductsViewHolder {
        return FavoriteProductsViewHolder(
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: FavoriteProductsViewHolder, position: Int) = holder.bind(getItem(position))


    class FavoriteProductsViewHolder(
        private val binding: ItemProductHomeBinding,
        private val onFavoriteProductClick: (Int) -> Unit,
        private val onDeleteClick: (ProductUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) {
            with(binding) {
                val strList = product.title?.split(" ")

                tvProductName.text = product.title
                tvCategory.text = product.category

                tvPrice.text = buildString {
                    if (product.saleState) {
                        append("FLASH SALE: ")
                        append(product.salePrice)
                    } else {
                        append(product.price)
                    }
                    append(" ₺")
                }

                Glide.with(productIv).load(product.imageOne).into(productIv)

                root.setOnClickListener {
                    onFavoriteProductClick(product.id)
                }
            }
        }
    }

    class FavProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }
}