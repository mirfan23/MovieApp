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
import com.movieappfinal.databinding.FragmentRegisterBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpannableStringUtils
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, AuthViewModel>(FragmentRegisterBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    private lateinit var auth: FirebaseAuth

    override fun initView() = with(binding) {

        toolbarRegister.title = getString(R.string.register_appbar_title)
        tietEmailRegister.hint = getString(R.string.email_edit_text_hint)
        tilEmailRegister.helperText = getString(R.string.example_email)
        tietPasswordRegister.hint = getString(R.string.password_edit_text_hint)
        tilPasswordRegister.helperText = getString(R.string.minimum_character)
        btnRegister.text = getString(R.string.register_btn_text)
        termsCo()

        auth = Firebase.auth

        val spannable = SpannableString("Sudah memiliki akun? Masuk.")

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        spannable.setSpan(
            clickableSpan,
            spannable.indexOf("Masuk"),
            spannable.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvAlreadyHaveAccount.text = spannable
        binding.tvAlreadyHaveAccount.movementMethod = LinkMovementMethod.getInstance()


    }

    override fun initListener() = with(binding){
        btnRegister.setOnClickListener {
            val email = tietEmailRegister.text.toString().trim()
            val password = tietPasswordRegister.text.toString().trim()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
                    } else {
                        context?.let { ctx ->
                            CustomSnackbar.showSnackBar(
                                ctx,
                                binding.root,
                                "Gagal Register"
                            )
                        }
                    }
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