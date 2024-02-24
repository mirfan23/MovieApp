package com.movieappfinal.presentation.authentication

import android.text.method.LinkMovementMethod
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentProfileBinding
import com.movieappfinal.utils.SpannableStringUtils
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding, AuthViewModel>(FragmentProfileBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    private lateinit var auth: FirebaseAuth

    override fun initView() = with(binding){
        toolbarProfile.title = "Profile"
        tietProfile.hint = "Name"
        btnFinish.text = "Finish"
        termsCo()

        auth = Firebase.auth
    }

    override fun initListener() {
        binding.run {
            btnFinish.setOnClickListener {
                val profileUpdates = userProfileChangeRequest {
                    displayName = tietProfile.text.toString().trim()
                }
                auth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_profileFragment_to_dashboardFragment)
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