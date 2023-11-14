package com.glitch.cybernexus.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.ItemProductHomeBinding

class SearchAdapter(
    private val onProductClick: (Int) -> Unit
) : ListAdapter<ProductUI, SearchAdapter.SearchViewHolder>(SearchDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(getItem(position))

    class SearchViewHolder(
        private val binding: ItemProductHomeBinding, private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                tvProductName.text = product.title

                /*if(!product.saleState) {
                    tvPrice.text = "${product.price} ₺"
                } else {
                    tvPrice.text = "${product.salePrice} ₺"
                }*/

                ratingBar.rating = (product.rate).toFloat()

                Glide.with(productIv).load(product.imageOne).into(productIv)

                root.setOnClickListener {
                    onProductClick(product.id)
                }
            }
        }
    }

    class SearchDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }
}