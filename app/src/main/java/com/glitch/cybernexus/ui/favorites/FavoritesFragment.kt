package com.glitch.cybernexus.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.glitch.cybernexus.data.source.Database
import com.glitch.cybernexus.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val categoryFilterAdapter = CategoryFilterAdapter(
        onCategoryFilterClick = ::onCategoryFilterClick
    )
    private val favoriteProductsAdapter = FavoriteProductsAdapter(
        onFavoriteProductClick = ::onFavoriteProductClick
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            categoryRv.adapter = categoryFilterAdapter
            favoritesRv.adapter = favoriteProductsAdapter
            Database.addCategory("Fashion")
            Database.addCategory("Body Enhancement")
            Database.addCategory("Equipment")
            Database.addCategory("Android Hardware")

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
            categoryFilterAdapter.updateList(Database.getCategory())
            favoriteProductsAdapter.updateList(Database.getProduct())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryFilterClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }

    private fun onFavoriteProductClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }
}