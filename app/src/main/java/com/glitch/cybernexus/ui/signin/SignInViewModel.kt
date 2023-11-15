package com.glitch.cybernexus.ui.signin

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
class SignInViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private var _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> get() = _signInState

    fun signIn(email: String, password: String) = viewModelScope.launch {
        if (checkFields(email, password)) {
            _signInState.value = SignInState.Loading

            _signInState.value = when (val result = firebaseRepository.signIn(email, password)) {
                is Resource.Success -> SignInState.Success
                is Resource.Fail -> SignInState.ShowMessage(result.failMessage)
                is Resource.Error -> SignInState.ShowMessage(result.errorMessage)
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            _signInState.value = SignInState.ShowMessage("Please fill in your e-mail")
            return false
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
            _signInState.value = SignInState.ShowMessage("Please check your email format")
            return false
        }

        if (password.isEmpty()) {
            _signInState.value = SignInState.ShowMessage("Please fill in your password")
            return false
        }

        if (password.length < 6) {
            _signInState.value =
                SignInState.ShowMessage("Password cannot be less than 6 characters")
            return false

        } else {
            return true
        }
    }
}

sealed interface SignInState {
    object Loading : SignInState
    object Success : SignInState
    data class ShowMessage(val errorMessage: String) : SignInState
}