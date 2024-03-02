package com.movieappfinal.presentation.authentication

import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.movieappfinal.R
import com.movieappfinal.core.domain.state.onCreated
import com.movieappfinal.core.domain.state.onValue
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentLoginBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpannableStringUtils
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
    BaseFragment<FragmentLoginBinding, AuthViewModel>(FragmentLoginBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun initView() = with(binding) {

        toolbarLogin.title = getString(R.string.login_appbar_title)
        btnLogin.text = getString(R.string.btn_login_text)
        tietEmailLogin.hint = getString(R.string.email_edit_text_hint)
        tietPasswordLogin.hint = getString(R.string.password_edit_text_hint)
        termsCo()

        val spannable = SpannableString(getString(R.string.doesnt_have_account))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        spannable.setSpan(
            clickableSpan,
            spannable.indexOf(getString(R.string.register_text)),
            spannable.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvDoesntHaveAccount.text = spannable
        binding.tvDoesntHaveAccount.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun initListener() {
        binding.apply {
            tietEmailLogin.doOnTextChanged { text, _, _, _ ->
                viewModel.validateLoginEmail(text.toString())
                enableLoginButtonIfValid()
            }
            tietPasswordLogin.doOnTextChanged { text, _, _, _ ->
                viewModel.validateLoginPassword(text.toString())
                enableLoginButtonIfValid()
            }
            btnLogin.setOnClickListener {
                val email = tietEmailLogin.text.toString().trim()
                val password = tietPasswordLogin.text.toString().trim()

                if (tilEmailLogin.isErrorEnabled.not() && tilPasswordLogin.isErrorEnabled.not()) {
                    viewModel.validateLoginField(email, password)
                }
//                viewModel.firebaseAnalytic("LOGIN")
                analytics("LOGIN")
            }
        }
    }

    override fun observeData() {
        with(viewModel) {
            validateLoginEmail.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilEmailLogin.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilEmailLogin.error = null
                            } else tilEmailLogin.error =
                                getString(R.string.email_is_required)
                        }
                    }
            }
            validateLoginPassword.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilPasswordLogin.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilPasswordLogin.error = null
                            } else tilPasswordLogin.error =
                                getString(R.string.password_is_required)
                        }
                    }
            }
            validateLoginField.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isPass ->
                        binding.run {
                            tilEmailLogin.isErrorEnabled = isPass.not()
                            tilPasswordLogin.isErrorEnabled = isPass.not()
                            if (isPass) {
                                val email = tietEmailLogin.text.toString().trim()
                                val password = tietPasswordLogin.text.toString().trim()

                                viewModel.signInWithFirebase(email, password)
                                    .launchAndCollectIn(viewLifecycleOwner) {
                                        if (it) {
                                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                                        } else {
                                            context?.let { ctx ->
                                                CustomSnackbar.showSnackBar(
                                                    ctx,
                                                    binding.root,
                                                    getString(R.string.login_failed)
                                                )
                                            }
                                        }
                                    }
                            }
                            viewModel.resetValidateLoginField()
                        }
                    }
            }
        }
    }

    private fun termsCo() {
        val sk = binding.tvTermCondition
        val fullText = getString(R.string.term_condition_login)
        val defaultLocale = resources.configuration.locales[0].language
        sk.text =
            context?.let { SpannableStringUtils.applyCustomTextColor(defaultLocale, it, fullText) }
        sk.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun analytics(data: String) {
        val bundle = Bundle()
        bundle.putString("show_message", data)
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    private fun enableLoginButtonIfValid() {
        binding.btnLogin.isEnabled =
            binding.tilEmailLogin.error.isNullOrEmpty() && binding.tilPasswordLogin.error.isNullOrEmpty()
    }
}