package com.glitch.cybernexus.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.cybernexus.common.Resource
import com.glitch.cybernexus.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private var _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> get() = _signUpState

    fun signUp(email: String, password: String) = viewModelScope.launch {
        if (checkFields(email, password)) {
            _signUpState.value = SignUpState.Loading

            _signUpState.value = when (val result = firebaseRepository.signUp(email, password)) {
                is Resource.Success -> SignUpState.Success
                is Resource.Fail -> SignUpState.ShowMessage(result.failMessage)
                is Resource.Error -> SignUpState.ShowMessage(result.errorMessage)
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            _signUpState.value = SignUpState.ShowMessage("Please fill in your e-mail")
            return false
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
            _signUpState.value = SignUpState.ShowMessage("Please check your email format")
            return false
        }

        if (password.isEmpty()) {
            _signUpState.value = SignUpState.ShowMessage("Please fill in your password")
            return false
        }

        if (password.length < 6) {
            _signUpState.value =
                SignUpState.ShowMessage("Password cannot be less than 6 characters")
            return false

        } else {
            return true
        }
    }
}

sealed interface SignUpState {
    object Loading : SignUpState
    object Success : SignUpState
    data class ShowMessage(val errorMessage: String) : SignUpState
}