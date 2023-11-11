package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Product
import com.glitch.cybernexus.databinding.ItemProductHomeBinding

/*class ProductAdapter(
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
}*/

class ProductAdapter(
    private val onAllProductClick: (Int) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onAllProductClick
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemProductHomeBinding, private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                tvProductName.text = product.title
                tvCompany.text = buildString {
                    append(product.price)
                    append(" ₺")
                }

                //Glide.with(ivProduct).load(product.imageOne).into(ivProduct)

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}