package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Product
import com.glitch.cybernexus.databinding.ItemSaleBinding

class SaleAdapter(
    private val onSaleClick: (String) -> Unit
) : RecyclerView.Adapter<SaleAdapter.SaleViewHolder>() {

    private val saleItemList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val binding = ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleViewHolder(binding, onSaleClick)
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        holder.bind(saleItemList[position])
    }

    override fun getItemCount(): Int {
        return saleItemList.size
    }

    class SaleViewHolder(
        private val binding: ItemSaleBinding, val onProductClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sale: Product) {
            with(binding) {
                tvProductName.text = sale.title
                tvCategorySale.text = sale.description

                root.setOnClickListener {
                    sale.title?.let { it1 -> onProductClick(it1) }
                }
            }
        }
    }

    fun updateList(list: List<Product>) {
        saleItemList.clear()
        saleItemList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}