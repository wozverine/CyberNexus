package com.glitch.cybernexus.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
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

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)
        Log.v("aaaaaaaaaaaaaaaa", args.id.toString())


        observeData()


        with(binding) {
            btnAddToCart.setOnClickListener {
                viewModel.addToCart(args.id)
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> progressBar.visible()

                is DetailState.SuccessState -> {

                    Glide.with(imageSlider).load(state.product.imageOne).into(imageSlider)
                    progressBar.gone()
                    ratingBar.visible()
                    imageSlider.visible()
                    //ivFav.visible()

                    tvProductName.text = state.product.title
                    tvPrice.text = "${state.product.price}"
                    tvDetails.text = state.product.description
                    ratingBar.rating = state.product.rate.toFloat()
                    //tvCategory.text = state.product.category

                    /*if (!state.product.saleState) {
                        tvSalePrice.gone()
                    } else {
                        tvSalePrice.text = "${state.product.salePrice}"
                    }*/

                    /*ivFav.setBackgroundResource(
                        if (state.product.isFav) {
                            R.drawable.ic_fav_selected
                        } else {
                            R.drawable.ic_fav_unselected
                        }
                    )*/
                    Log.v("aaaaaaaaaaaaaaaa",state.product.title)
                }

                is DetailState.EmptyScreen -> {
                    progressBar.gone()
                    tvError.text = state.failMessage
                    tvError.visible()
                    ivError.visible()
                }

                is DetailState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                    Log.v("aaaaaaaaaaaaaaaa",state.errorMessage)
                }

            }
        }
    }
}