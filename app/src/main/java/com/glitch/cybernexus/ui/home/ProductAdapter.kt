package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Product
import com.glitch.cybernexus.databinding.ItemProductHomeBinding

class ProductAdapter(
    private val onAllProductClick: (String) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val allProductList = mutableListOf<Product>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onAllProductClick)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        holder.bind(allProductList[position])
    }

    override fun getItemCount(): Int {
        return allProductList.size
    }

    class ProductViewHolder(
        private val binding: ItemProductHomeBinding, val onAllProductClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                tvProductName.text = product.title
                tvCompany.text = product.company

                root.setOnClickListener{
                    onAllProductClick(product.company)
                }
            }
        }
    }
}