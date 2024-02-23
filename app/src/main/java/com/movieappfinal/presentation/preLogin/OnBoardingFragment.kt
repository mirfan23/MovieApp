package com.movieappfinal.presentation.preLogin

import android.os.Handler
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.movieappfinal.R
import com.movieappfinal.adapter.OnBoardingAdapter
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentOnBoardingBinding
import com.movieappfinal.viewmodel.AuthViewModel
import kotlinx.coroutines.Runnable
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding, AuthViewModel>(FragmentOnBoardingBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()
    private lateinit var viewPager: ViewPager2
    private lateinit var onBoardingAdapter: OnBoardingAdapter
    private lateinit var tabLayout: TabLayout


    override fun initView() = with(binding){
        btnLoginOnBoarding.text = getString(R.string.btn_login_text)
        btnRegisterOnBoarding.text = getString(R.string.register_btn_text)
        autoScrollOnBoarding()

    }

    override fun initListener() {
        viewPager = binding.vpOnboarding
        onBoardingAdapter = OnBoardingAdapter()
        viewPager.adapter = onBoardingAdapter

        tabLayout = binding.tlOnboarding
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

    private fun autoScrollOnBoarding() {
        val handler = Handler()
        val update = Runnable {
            if (viewPager.currentItem < onBoardingAdapter.itemCount - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else viewPager.currentItem = 0
        }
        val delay = 1500L

        handler.postDelayed(object : Runnable {
            override fun run() {
                update.run()
                handler.postDelayed(
                    this,
                    delay
                )
            }
        }, delay)
    }

}