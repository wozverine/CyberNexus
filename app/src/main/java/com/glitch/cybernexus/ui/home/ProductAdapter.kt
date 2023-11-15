package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.R
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.ItemProductHomeBinding


class ProductAdapter(
    private val onAllProductClick: (Int) -> Unit, private val onFavProductClick: (ProductUI) -> Unit
) : ListAdapter<ProductUI, ProductAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onAllProductClick,
            onFavProductClick
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemProductHomeBinding,
        private val onProductClick: (Int) -> Unit,
        private val onFavProductClick: (ProductUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
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

                btnFav.setBackgroundResource(
                    if (product.isFav) {
                        R.drawable.icon_fav_selected
                    } else {
                        R.drawable.icon_fav_unselected
                    }
                )

                ratingBar.rating = (product.rate).toFloat()

                Glide.with(productIv).load(product.imageOne).into(productIv)

                root.setOnClickListener {
                    onProductClick(product.id)
                }

                btnFav.setOnClickListener {
                    onFavProductClick(product)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }
}