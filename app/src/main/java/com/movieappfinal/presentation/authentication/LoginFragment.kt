package com.movieappfinal.presentation.authentication

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentLoginBinding
import com.movieappfinal.utils.SpannableStringUtils
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>(FragmentLoginBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    private lateinit var auth: FirebaseAuth

    override fun initView() = with(binding) {

        toolbarLogin.title = getString(R.string.login_appbar_title)
        btnLogin.text = getString(R.string.btn_login_text)
        tietEmailLogin.hint = getString(R.string.email_edit_text_hint)
        tietPasswordLogin.hint = getString(R.string.password_edit_text_hint)
        termsCo()

        auth = Firebase.auth

        val spannable = SpannableString("Belum memiliki akun? Daftar.")

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        spannable.setSpan(
            clickableSpan,
            spannable.indexOf("Daftar"),
            spannable.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvDoesntHaveAccount.text = spannable
        binding.tvDoesntHaveAccount.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun initListener() {
        binding.apply {
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            }
        }
    }

    override fun observeData() {}

    private fun termsCo() {
        val sk = binding.tvTermCondition
        val fullText = getString(R.string.term_condition_login)
        val defaultLocale = resources.configuration.locales[0].language
        sk.text = context?.let { SpannableStringUtils.applyCustomTextColor(defaultLocale, it, fullText) }
        sk.movementMethod = LinkMovementMethod.getInstance()
    }
}