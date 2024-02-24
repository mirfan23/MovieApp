package com.movieappfinal.presentation.preLogin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentSearchBinding
import com.movieappfinal.databinding.FragmentSplashScreenBinding
import com.movieappfinal.utils.Constant
import com.movieappfinal.utils.Constant.ANIMATION_DELAY
import com.movieappfinal.utils.Constant.ANIMATION_START
import com.movieappfinal.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, AuthViewModel>(FragmentSplashScreenBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {
        animation()
        lifecycleScope.launch {
            delay(ANIMATION_DELAY)
            findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
        }
    }

    override fun initListener() {}

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