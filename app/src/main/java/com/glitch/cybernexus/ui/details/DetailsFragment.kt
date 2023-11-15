package com.glitch.cybernexus.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.gone
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.common.visible
import com.glitch.cybernexus.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    private val imageSliderAdapter = ImageSliderAdapter()

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)

        observeData()

        with(binding) {
            btnAddToCart.setOnClickListener {
                viewModel.addToCart(args.id)
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnFav.setOnClickListener {
                viewModel.setFavState(args.id)
            }
        }
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> progressBar.visible()

                is DetailState.SuccessState -> {

                    viewPager.adapter = imageSliderAdapter
                    imageSliderAdapter.updateList(
                        listOf(
                            state.product.imageOne, state.product.imageTwo, state.product.imageThree
                        )
                    )

                    progressBar.gone()
                    ratingBar.visible()
                    viewPager.visible()
                    btnFav.visible()

                    tvDetails.text = state.product.description
                    ratingBar.rating = state.product.rate.toFloat()

                    val strList = state.product.title?.split(" ")
                    tvProductName.text = strList?.subList(1, strList.size)?.joinToString()
                    tvCompany.text = strList?.get(0)

                    tvPrice.text = buildString {
                        if (!state.product.saleState) {
                            append("FLASH SALE: ")
                            append(state.product.salePrice)
                        } else {
                            append(state.product.price)
                        }
                        append(" ₺")
                    }

                    btnFav.setBackgroundResource(
                        if (state.product.isFav) {
                            R.drawable.icon_fav_selected
                        } else {
                            R.drawable.icon_fav_unselected
                        }
                    )
                }

                is DetailState.EmptyScreen -> {
                    progressBar.gone()
                    tvError.text = state.failMessage
                    tvError.visible()
                    ivError.visible()
                }

                is DetailState.ShowMessage -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 2000).show()
                }
            }
        }
    }
}