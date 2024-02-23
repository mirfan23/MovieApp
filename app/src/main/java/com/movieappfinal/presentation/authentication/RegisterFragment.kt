package com.movieappfinal.presentation.authentication

import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentRegisterBinding
import com.movieappfinal.utils.CustomSnackbar
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
        tvToLogin.text = getString(R.string.log_in_with)
        btnToLogin.text = getString(R.string.btn_login_text)

        auth = Firebase.auth


    }

    override fun initListener() = with(binding){
        btnRegister.setOnClickListener {
            val email = tietEmailRegister.text.toString().trim()
            val password = tietPasswordRegister.text.toString().trim()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("MASUK: Berhasil Register")
                        findNavController()
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

}