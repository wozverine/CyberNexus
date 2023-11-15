package com.glitch.cybernexus.ui.signup

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.gone
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.common.visible
import com.glitch.cybernexus.databinding.FragmentSignupBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_signup) {
    private val binding by viewBinding(FragmentSignupBinding::bind)

    private val viewModel: SignUpViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

        observeData()

        with(binding) {
            signupNextBtn.setOnClickListener() {
                viewModel.signUp(
                    etMailSignIn.text.toString(), etPasswordSignUp.text.toString()
                )
            }
            spannableText(
                getString(R.string.login), signupTv, R.id.action_signUpFragment_to_signInFragment
            )
        }
    }

    private fun spannableText(text: String, tv: TextView, navigate: Int) {
        val spannableString = SpannableString(text)
        val index = text.indexOf("?") + 1
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                findNavController().navigate(navigate)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED
                ds.isUnderlineText = false
            }
        }
        spannableString.setSpan(
            clickableSpan, index, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv.setText(spannableString, TextView.BufferType.SPANNABLE)
        tv.movementMethod = LinkMovementMethod.getInstance()
        tv.highlightColor = Color.TRANSPARENT
    }

    private fun observeData() = with(binding) {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignUpState.Loading -> progressBar.visible()

                is SignUpState.Success -> {
                    progressBar.gone()
                    sharedPref.edit().putBoolean("isLogin", true).apply()
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                }

                is SignUpState.ShowMessage -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}