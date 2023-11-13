package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glitch.cybernexus.data.model.response.ProductUI
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
    private val onAllProductClick: (Int) -> Unit,
    private val onFavProductClick: (ProductUI) -> Unit
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
        private val onFavClick: (ProductUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                tvProductName.text = product.title
                tvCompany.text = buildString {
                    append(product.price)
                    append(" ₺")
                }
                /*ivFavorite.setBackgroundResource(
                    if (product.isFav) R.drawable.ic_fav_selected
                    else R.drawable.ic_fav_unselected
                )*/

                Glide.with(productIv).load(product.imageOne).into(productIv)

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }

                /*ivFavorite.setOnClickListener {
                    onFavProductClick(product)
                }*/
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