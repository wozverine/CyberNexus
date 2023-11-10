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
        parent: ViewGroup, viewType: Int
    ): ProductViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onAllProductClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
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
                val strList = product.title?.split(" ")

                tvProductName.text = product.title
                tvCompany.text = strList?.get(0)

                root.setOnClickListener {
                    product.title?.let { it1 -> onAllProductClick(it1) }
                }
            }
        }
    }

    fun updateList(list: List<Product>) {
        allProductList.clear()
        allProductList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}