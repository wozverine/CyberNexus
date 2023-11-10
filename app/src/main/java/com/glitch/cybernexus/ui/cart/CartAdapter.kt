package com.glitch.cybernexus.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Product
import com.glitch.cybernexus.databinding.ItemProductHomeBinding

class CartAdapter(
    private val onCartClicked: (String) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val cartList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onCartClicked)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class CartViewHolder(
        private val binding: ItemProductHomeBinding, val onCartClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                val strList = product.title?.split(" ")

                tvProductName.text = product.title
                tvCompany.text = strList?.get(0)

                root.setOnClickListener {
                    product.title?.let { it1 -> onCartClicked(it1) }
                }
            }

        }
    }

    fun updateList(list: List<Product>) {
        cartList.clear()
        cartList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}