package com.glitch.cybernexus.ui.signin

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.databinding.FragmentSigninBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loginNextBtn.setOnClickListener() {
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
            spannableText(
                getString(R.string.signup_long),
                signupTv,
                R.id.action_signInFragment_to_signUpFragment
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}