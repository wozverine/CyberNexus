package com.glitch.cybernexus.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.cybernexus.data.repository.FirebaseRepository
import com.glitch.cybernexus.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _paymentState = MutableLiveData<PaymentState>()
    val paymentState: LiveData<PaymentState> get() = _paymentState

    fun orderPayment(
        fullName: String, cardNumber: String, expiryMonth: String, expiryYear: String, cvc: String
    ) = viewModelScope.launch {
        _paymentState.value = PaymentState.Loading

        val errorMessage = when {
            fullName.isEmpty() -> "Please enter your name"
            cardNumber.isEmpty() || cardNumber.length != 16 -> "Card number invalid"
            expiryMonth.isEmpty() -> "Please enter the expiry date"
            expiryYear.isEmpty() -> "Please enter the expiry date"
            cvc.isEmpty() || cvc.length < 3 -> "CVC invalid"
            else -> null
        }

        if (errorMessage != null) {
            _paymentState.value = PaymentState.ShowPopUp(errorMessage)
        } else {
            _paymentState.value = PaymentState.GoSuccess
            productRepository.clearCart(firebaseRepository.getUserId())
        }
    }
}

sealed interface PaymentState {
    object Loading : PaymentState
    object GoSuccess : PaymentState
    data class ShowPopUp(val errorMessage: String) : PaymentState
}