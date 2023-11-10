package com.glitch.cybernexus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.glitch.cybernexus.data.source.Database
import com.glitch.cybernexus.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val saleAdapter = SaleAdapter(
        onSaleClick = ::onSaleClick
    )

    private val productAdapter = ProductAdapter(
        onAllProductClick = ::onProductClick
    )

    private val categoryAdapter = CategoryAdapter(
        onCategoryClick = ::onCategoryClick
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            flashSaleRv.adapter = saleAdapter
            allProductsRv.adapter = productAdapter
            categoriesRv.adapter = categoryAdapter
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true
            )
            saleAdapter.updateList(Database.getProduct())
            productAdapter.updateList(Database.getProduct())
            Database.addCategory("Fashion")
            Database.addCategory("Body Enhancement")
            Database.addCategory("Equipment")
            Database.addCategory("Android Hardware")
            categoryAdapter.updateList(Database.getCategory())
        }
    }

    private fun onProductClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }/*private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        with(dialogBinding) {
            button.setOnClickListener() {
                val title = textView.text.toString()
                val desc = textView3.text.toString()

                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    Database.addDailyNotes(title, desc)
                    //binding.dailyNotesRv.adapter = dailyNotesAdapter
                    dailyNotesAdapter.updateList(Database.getDailyNotes())
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(), getString(R.string.fill_blanks), Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        dialog.show()
    }*/

    private fun onSaleClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }

    private fun onCategoryClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}