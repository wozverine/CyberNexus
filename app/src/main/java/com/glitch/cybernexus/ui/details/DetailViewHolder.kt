package com.glitch.cybernexus.ui.details

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
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    private var detailData: ProductUI? = null

    fun getProductDetail(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = productRepository.getProductDetail(id)) {
            is Resource.Success -> {
                detailData = result.data
                DetailState.SuccessState(result.data)
            }

            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowMessage(result.errorMessage)
        }
    }

    fun addToCart(productId: Int) = viewModelScope.launch {
        productRepository.addToCart(firebaseRepository.getUserId(), productId)
    }

}

sealed interface DetailState {
    object Loading : DetailState
    data class SuccessState(val product: ProductUI) : DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
    data class ShowMessage(val errorMessage: String) : DetailState
}