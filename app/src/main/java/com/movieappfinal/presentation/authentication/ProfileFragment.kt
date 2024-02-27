package com.movieappfinal.presentation.authentication

import android.text.method.LinkMovementMethod
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.movieappfinal.R
import com.movieappfinal.core.domain.state.onCreated
import com.movieappfinal.core.domain.state.onValue
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentProfileBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpannableStringUtils
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, AuthViewModel>(FragmentProfileBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    private lateinit var auth: FirebaseAuth

    override fun initView() = with(binding){
        toolbarProfile.title = getString(R.string.profile_title)
        tietProfile.hint = getString(R.string.name_hint)
        btnFinish.text = getString(R.string.finish_text)
        termsCo()

        auth = Firebase.auth
    }

    override fun initListener() {
        binding.run {
            tietProfile.doOnTextChanged { text, _, _, _ ->
                viewModel.validateProfileName(text.toString())
            }
            btnFinish.setOnClickListener {
                val profileUpdates = userProfileChangeRequest {
                    displayName = tietProfile.text.toString().trim()
                }
                viewModel.updateProfile(profileUpdates).launchAndCollectIn(viewLifecycleOwner) {
                    if (it){
                        findNavController().navigate(R.id.action_profileFragment_to_dashboardFragment)
                    } else {
                        context?.let { ctx ->
                            CustomSnackbar.showSnackBar(
                                ctx,
                                binding.root,
                                getString(R.string.failed_to_add_profile_name)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun observeData() {
        with(viewModel){
            validateProfileName.launchAndCollectIn(viewLifecycleOwner){state ->
                state.onCreated {}
                    .onValue {isValid->
                        binding.run {
                            tilProfile.isErrorEnabled = isValid.not()
                            if (isValid) {
                                tilProfile.error = null
                            } else tilProfile.error = getString(R.string.name_can_not_be_null)
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