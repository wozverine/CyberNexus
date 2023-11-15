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
                    btnFav.visible()

                    //tvProductName.text = state.product.title
                    tvDetails.text = state.product.description
                    ratingBar.rating = state.product.rate.toFloat()
                    //tvCategory.text = state.product.category
                    val strList = state.product.title?.split(" ")
                    tvProductName.text = strList?.subList(1,strList.size)?.joinToString ()
                    tvCompany.text = strList?.get(0)

                    if (!state.product.saleState) {
                        tvPrice.text = "${state.product.price}"
                    } else {
                        tvPrice.text = "${state.product.salePrice}"
                    }

                    btnFav.setImageResource(
                        if (state.product.isFav) {
                        R.drawable.icon_fav_selected
                    } else {
                        R.drawable.icon_fav_unselected
                    }

                    )
                    Log.v("aaaaaaaaaaaaaaaa",state.product.title)
                }

                is DetailState.EmptyScreen -> {
                    progressBar.gone()
                    tvError.text = state.failMessage
                    tvError.visible()
                    ivError.visible()
                }

                is DetailState.ShowMessage -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                    Log.v("aaaaaaaaaaaaaaaa",state.errorMessage)
                }

            }
        }
    }
}