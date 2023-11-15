package com.glitch.cybernexus.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.cybernexus.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    private var _splashState = MutableLiveData<SplashState>()
    val splashState: LiveData<SplashState> get() = _splashState

    init {
        viewModelScope.launch {
            delay(3000)
            checkLogin()
        }
    }

    private fun checkLogin() = viewModelScope.launch {
        if (firebaseRepository.isUserLoggedIn()) {
            _splashState.value = SplashState.GoHome
        } else {
            _splashState.value = SplashState.GoSignIn
        }
    }
}

sealed interface SplashState {
    object GoHome : SplashState
    object GoSignIn : SplashState
}