package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.data.model.Product
import com.glitch.cybernexus.databinding.ItemSaleBinding

class SaleAdapter(
    private val onSaleClick: (Int) -> Unit
) : ListAdapter<Product, SaleAdapter.SaleViewHolder>(SalesDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleAdapter.SaleViewHolder {
        return SaleAdapter.SaleViewHolder(
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onSaleClick
        )
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SaleViewHolder(
        private val binding: ItemSaleBinding, private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sale: Product) {
            with(binding) {
                tvProductName.text = sale.title
                tvCategorySale.text = sale.category

                Glide.with(productIv).load(sale.imageOne).into(productIv)

                root.setOnClickListener {
                    onProductClick(sale.id ?: 1)
                }
            }
        }
    }

    class SalesDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}