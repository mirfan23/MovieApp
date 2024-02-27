package com.movieappfinal.presentation.preLogin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.movieappfinal.R
import com.movieappfinal.core.domain.state.SplashState
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentSearchBinding
import com.movieappfinal.databinding.FragmentSplashScreenBinding
import com.movieappfinal.utils.Constant
import com.movieappfinal.utils.Constant.ANIMATION_DELAY
import com.movieappfinal.utils.Constant.ANIMATION_START
import com.movieappfinal.viewmodel.AuthViewModel
import com.movieappfinal.viewmodel.DashboardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, DashboardViewModel>(FragmentSplashScreenBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()

    override fun initView() {
        viewModel.getOnBoardingState()
        animation()
        lifecycleScope.launch {
            delay(ANIMATION_DELAY)
        }
    }

    override fun initListener() {
        with(viewModel) {
            onBoarding.launchAndCollectIn(viewLifecycleOwner) { state ->
                Handler(Looper.getMainLooper()).postDelayed({
                    val navController =
                        activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                            ?.findNavController()
                    when (state) {
                        is SplashState.OnBoarding -> {
                            navController?.navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
                        }
                        is SplashState.Dashboard -> {
                            navController?.navigate(R.id.action_splashScreenFragment_to_dashboardFragment)
                        }
                        is SplashState.Profile -> {
                            navController?.navigate(R.id.action_splashScreenFragment_to_profileFragment)
                        }
                        else -> {
                            navController?.navigate(R.id.action_splashScreenFragment_to_loginFragment)
                        }
                    }
                }, 1000L)

            }
        }
    }

    override fun observeData() {}

    private fun animation() {
        val leftAnimatorDown = ObjectAnimator.ofFloat(
            binding.splashScreenLogo,
            View.TRANSLATION_Y,
            ANIMATION_START,
            400f
        )
        val rightAnimatorDown = ObjectAnimator.ofFloat(
            binding.splashScreenLogo2,
            View.TRANSLATION_Y,
            ANIMATION_START,
            400f
        )
        val leftAnimatorMove = ObjectAnimator.ofFloat(
            binding.splashScreenLogo,
            View.TRANSLATION_X,
            ANIMATION_START,
            50f
        )
        val rightAnimatorMove = ObjectAnimator.ofFloat(
            binding.splashScreenLogo2,
            View.TRANSLATION_X,
            ANIMATION_START,
            -50f
        )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            leftAnimatorDown,
            leftAnimatorMove,
            rightAnimatorDown,
            rightAnimatorMove
        )
        animatorSet.duration = ANIMATION_DELAY
        animatorSet.start()
    }
}