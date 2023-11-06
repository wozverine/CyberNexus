package com.glitch.cybernexus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glitch.cybernexus.data.model.Product
import com.glitch.cybernexus.databinding.ItemCartBinding

class SaleAdapter(
    private val onProductClick: (String) -> Unit
) : RecyclerView.Adapter<SaleAdapter.SaleViewHolder>() {

    private val noteList = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleViewHolder(binding, onProductClick)
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class SaleViewHolder(
        private val binding: ItemCartBinding, val onProductClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Product) {
            with(binding) {
                tv1.text = note.title
                tv2.text = note.description

                root.setOnClickListener {
                    onProductClick(note.description)
                }
            }
        }
    }

    fun updateList(list: List<Product>) {
        noteList.clear()
        noteList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}