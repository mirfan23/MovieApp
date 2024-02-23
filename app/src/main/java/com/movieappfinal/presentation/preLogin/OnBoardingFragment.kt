package com.movieappfinal.presentation.preLogin

import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.movieappfinal.R
import com.movieappfinal.adapter.OnBoardingAdapter
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentOnBoardingBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding, AuthViewModel>(FragmentOnBoardingBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() = with(binding){
        btnLoginOnBoarding.text = getString(R.string.btn_login_text)
        btnRegisterOnBoarding.text = getString(R.string.register_btn_text)

    }

    override fun initListener() {
        val viewPager = binding.vpOnboarding
        val onBoardingAdapter = OnBoardingAdapter()
        viewPager.adapter = onBoardingAdapter

        val tabLayout = binding.tlOnboarding
        TabLayoutMediator(tabLayout, viewPager) {_,_ ->}.attach()

        binding.apply {
            btnLoginOnBoarding.setOnClickListener {
                findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
            }
            btnRegisterOnBoarding.setOnClickListener {
                findNavController().navigate(R.id.action_onBoardingFragment_to_registerFragment)
            }
        }

    }

    override fun observeData() {}



}