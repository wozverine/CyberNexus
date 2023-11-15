package com.glitch.cybernexus.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.gone
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.common.visible
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel by viewModels<FavoritesViewModel>()

    private val favoriteProductsAdapter = FavoriteProductsAdapter(
        onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites()

        observeData()

        with(binding) {
            favoritesRv.adapter = favoriteProductsAdapter
        }
    }


    private fun observeData() = with(binding) {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                FavoritesState.Loading -> {
                    progressBar.visible()
                    favoritesRv.gone()
                }

                is FavoritesState.SuccessState -> {
                    progressBar.gone()
                    favoriteProductsAdapter.submitList(state.products)
                    favoritesRv.visible()
                    favoritesTv.visible()
                }

                is FavoritesState.EmptyScreen -> {
                    favoritesRv.gone()
                    progressBar.gone()
                    favoritesTv.visible()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is FavoritesState.ShowMessage -> {
                    progressBar.gone()
                    favoritesTv.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                id
            )
        )
    }

    private fun onDeleteClick(product: ProductUI) {
        viewModel.deleteFromFavorites(product)
    }
}

