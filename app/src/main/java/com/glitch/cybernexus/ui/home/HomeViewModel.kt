package com.glitch.cybernexus.ui.home

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
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    private var _saleState = MutableLiveData<SaleState>()
    val saleState: LiveData<SaleState> get() = _saleState

    fun getProducts() = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value = when (val result = productRepository.getProducts()) {
            is Resource.Success -> HomeState.SuccessState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowMessage(result.errorMessage)
        }
    }

    fun getSaleProducts() = viewModelScope.launch {
        _saleState.value = SaleState.Loading

        _saleState.value = when (val result = productRepository.getSaleProducts()) {
            is Resource.Success -> SaleState.SuccessState(result.data)
            is Resource.Fail -> SaleState.EmptyScreen(result.failMessage)
            is Resource.Error -> SaleState.ShowMessage(result.errorMessage)
        }
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product)
        } else {
            productRepository.addToFavorites(product)
        }
        getProducts()
        getSaleProducts()
    }

    fun logOut() = viewModelScope.launch {
        firebaseRepository.logOut()
        _homeState.value = HomeState.GoToSignIn
    }

    fun clearFavorites() = viewModelScope.launch {
        productRepository.clearFavorites()
    }
}

sealed interface HomeState {
    object Loading : HomeState
    object GoToSignIn : HomeState
    data class SuccessState(val products: List<ProductUI>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowMessage(val errorMessage: String) : HomeState
}

sealed interface SaleState {
    object Loading : SaleState
    data class SuccessState(val products: List<ProductUI>) : SaleState
    data class EmptyScreen(val failMessage: String) : SaleState
    data class ShowMessage(val errorMessage: String) : SaleState
}
