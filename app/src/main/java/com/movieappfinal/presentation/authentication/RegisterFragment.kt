package com.movieappfinal.presentation.authentication

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.movieappfinal.R
import com.movieappfinal.core.domain.state.onCreated
import com.movieappfinal.core.domain.state.onValue
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentRegisterBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpannableStringUtils
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, AuthViewModel>(FragmentRegisterBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() = with(binding) {

        toolbarRegister.title = getString(R.string.register_appbar_title)
        tietEmailRegister.hint = getString(R.string.email_edit_text_hint)
        tilEmailRegister.helperText = getString(R.string.example_email)
        tietPasswordRegister.hint = getString(R.string.password_edit_text_hint)
        tilPasswordRegister.helperText = getString(R.string.minimum_character)
        btnRegister.text = getString(R.string.register_btn_text)
        termsCo()

        val spannable = SpannableString(getString(R.string.already_have_account))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        spannable.setSpan(
            clickableSpan,
            spannable.indexOf(getString(R.string.login_text)),
            spannable.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvAlreadyHaveAccount.text = spannable
        binding.tvAlreadyHaveAccount.movementMethod = LinkMovementMethod.getInstance()


    }

    override fun initListener() = with(binding){
        tietEmailRegister.doOnTextChanged { text, _, _, _ ->
            viewModel.validateRegisterEmail(text.toString())
        }
        tietPasswordRegister.doOnTextChanged { text, _, _, _ ->
            viewModel.validateRegisterPassword(text.toString())
        }
        btnRegister.setOnClickListener {
            val email = tietEmailRegister.text.toString().trim()
            val password = tietPasswordRegister.text.toString().trim()

            if (tilEmailRegister.isErrorEnabled.not() && tilPasswordRegister.isErrorEnabled.not()) {
                viewModel.validateRegisterField(email, password)
            }
        }
    }

    override fun observeData() {
        with(viewModel) {
            validateRegisterEmail.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilEmailRegister.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilEmailRegister.error = null
                            } else tilEmailRegister.error = getString(R.string.email_is_invalid)
                        }
                    }
            }
            validateRegisterPassword.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onCreated {}
                    .onValue { isValid ->
                        binding.run {
                            tilPasswordRegister.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilPasswordRegister.error = null
                            } else tilPasswordRegister.error =
                                getString(R.string.password_is_invalid)
                        }
                    }
            }
            validateRegisterField.launchAndCollectIn(viewLifecycleOwner) {state ->
                state.onCreated {}
                    .onValue{isPass ->
                        binding.run {
                            tilEmailRegister.isErrorEnabled = isPass.not()
                            tilPasswordRegister.isErrorEnabled = isPass.not()
                            if (isPass) {
                                val email = tietEmailRegister.text.toString().trim()
                                val password = tietPasswordRegister.text.toString().trim()

                                viewModel.signUpWithFirebase(email, password).launchAndCollectIn(viewLifecycleOwner) {
                                    if (it) {
                                        findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
                                    } else {
                                        context?.let { ctx ->
                                            CustomSnackbar.showSnackBar(
                                                ctx,
                                                binding.root,
                                                getString(R.string.register_failed)
                                            )
                                        }
                                    }
                                }
                            } else {
                                tilEmailRegister.error = getString(R.string.email_is_required)
                                tilPasswordRegister.error = getString(R.string.password_is_required)
                            }
                        }
                    }
            }
        }
    }

    private fun termsCo() {
        val sk = binding.tvTermCondition
        val fullText = getString(R.string.term_condition_login)
        val defaultLocale = resources.configuration.locales[0].language
        sk.text = context?.let { SpannableStringUtils.applyCustomTextColor(defaultLocale, it, fullText) }
        sk.movementMethod = LinkMovementMethod.getInstance()
    }
}
