package com.glitch.cybernexus.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.ItemCartBinding


class CartAdapter(
    private val onProductClick: (Int) -> Unit, private val onDeleteClick: (Int) -> Unit
) : ListAdapter<ProductUI, CartAdapter.CartViewHolder>(ProductDiffUtilCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(getItem(position))


    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val onProductClick: (Int) -> Unit,
        private val onDeleteClick: (Int) -> Unit
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

                Glide.with(productIv).load(product.imageOne).into(productIv)

                root.setOnClickListener {
                    onProductClick(product.id)
                }

                btnDelete.setOnClickListener {
                    onDeleteClick(product.id)
                }
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