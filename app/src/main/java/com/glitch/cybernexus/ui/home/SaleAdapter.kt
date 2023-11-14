package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.common.strike
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.ItemSaleBinding

class SaleAdapter(
    private val onSaleClick: (Int) -> Unit, private val onFavProductClick: (ProductUI) -> Unit
) : ListAdapter<ProductUI, SaleAdapter.SaleViewHolder>(SalesDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleAdapter.SaleViewHolder {
        return SaleAdapter.SaleViewHolder(
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onSaleClick,
            onFavProductClick
        )
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) =
        holder.bind(getItem(position))


    class SaleViewHolder(
        private val binding: ItemSaleBinding,
        private val onProductClick: (Int) -> Unit,
        private val onFavProductClick: (ProductUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) {
            with(binding) {
                tvProductName.text = product.title
                tvCategorySale.text = product.category
                tvPrice.text = buildString {
                    append(product.price)
                    append(" ₺")
                }
                tvPrice.strike = true
                tvSalePrice.text = buildString {
                    append(product.salePrice)
                    append(" ₺")
                }

                Glide.with(productIv).load(product.imageOne).into(productIv)

                root.setOnClickListener {
                    onProductClick(product.id)
                }
            }
        }
    }

    class SalesDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }
}