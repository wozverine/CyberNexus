package com.glitch.cybernexus.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.cybernexus.common.Resource
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.data.repository.FirebaseRepository
import com.glitch.cybernexus.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewHolder @Inject constructor(
    private val productRepository: ProductRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState

    private val _totalAmount = MutableLiveData(0.0)
    val totalAmount: LiveData<Double> = _totalAmount

    fun getCartProducts() = viewModelScope.launch {
        _cartState.value = CartState.Loading

        when (val result = productRepository.getCartProducts(firebaseRepository.getUserId())) {
            is Resource.Success -> {
                _cartState.value = CartState.SuccessState(result.data)
                _totalAmount.value = result.data.sumOf { product ->
                    if (product.saleState) {
                        product.salePrice
                    } else {
                        product.price
                    }
                }
            }

            is Resource.Error -> {
                _cartState.value = CartState.ShowMessage(result.errorMessage)
            }

            is Resource.Fail -> {
                _cartState.value = CartState.EmptyScreen(result.failMessage)
            }
        }
    }

    fun deleteFromCart(productId: Int) = viewModelScope.launch {
        productRepository.deleteFromCart(firebaseRepository.getUserId(), productId)
        getCartProducts()
        resetTotal()
    }

    fun clearCart() = viewModelScope.launch {
        productRepository.clearCart(firebaseRepository.getUserId())
        getCartProducts()
        resetTotal()
    }

    private fun resetTotal() {
        _totalAmount.value = 0.0
    }
}

sealed interface CartState {
    object Loading : CartState
    data class SuccessState(val products: List<ProductUI>) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
    data class ShowMessage(val errorMessage: String) : CartState
}