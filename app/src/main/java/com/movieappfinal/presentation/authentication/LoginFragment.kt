package com.movieappfinal.presentation.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentLoginBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>(FragmentLoginBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    private lateinit var auth: FirebaseAuth

    override fun initView() = with(binding) {

        toolbarLogin.title = getString(R.string.login_appbar_title)
        btnLogin.text = getString(R.string.btn_login_text)
        tvToRegister.text = getString(R.string.register_with)
        btnRegister.text = getString(R.string.register_btn_text)
        tietEmailLogin.hint = getString(R.string.email_edit_text_hint)
        tietPasswordLogin.hint = getString(R.string.password_edit_text_hint)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null) { }
    }

    override fun initListener() {}

    override fun observeData() {}

}