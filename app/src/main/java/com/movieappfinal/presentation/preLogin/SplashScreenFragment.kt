package com.movieappfinal.presentation.preLogin

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
import com.movieappfinal.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding, AuthViewModel>(FragmentSplashScreenBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {
//        val navController =
//            activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
//                ?.findNavController()
//        navController?.navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)

        animation()
        lifecycleScope.launch {
            delay(ANIMATION_DELAY)
            findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
        }
    }

    override fun initListener() {}

    override fun observeData() {}
    private fun animation() {
        val yellowAnimator = ObjectAnimator.ofFloat(
            binding.splashScreenYellow,
            View.ROTATION,
            Constant.ANIMATION_START,
            Constant.YELLOW_ROTATION
        )
        val redAnimator = ObjectAnimator.ofFloat(
            binding.splashScreenRed,
            View.ROTATION,
            Constant.ANIMATION_START,
            Constant.RED_ROTATION,
        )
        val redAnimatorTrans = ObjectAnimator.ofFloat(
            binding.splashScreenRed,
            View.TRANSLATION_X,
            Constant.ANIMATION_START,
            Constant.RED_TRANSLATION_X
        )
        val yellowAnimatorTrans = ObjectAnimator.ofFloat(
            binding.splashScreenYellow,
            View.TRANSLATION_X,
            Constant.ANIMATION_START,
            Constant.YELLOW_TRANSLATION_X
        )
        val redAnimatorTrans2 = ObjectAnimator.ofFloat(
            binding.splashScreenRed,
            View.TRANSLATION_Y,
            Constant.ANIMATION_START,
            Constant.RED_TRANSLATION_Y
        )
        val yellowAnimatorTrans2 = ObjectAnimator.ofFloat(
            binding.splashScreenYellow,
            View.TRANSLATION_Y,
            Constant.ANIMATION_START,
            Constant.YELLOW_TRANSLATION_Y
        )
        val greenAnimator = ObjectAnimator.ofFloat(
            binding.splashScreenGreen,
            View.TRANSLATION_Y,
            Constant.ANIMATION_START,
            Constant.GREEN_TRANSLATION_Y
        )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            yellowAnimator,
            yellowAnimatorTrans,
            yellowAnimatorTrans2,
            redAnimator,
            redAnimatorTrans,
            redAnimatorTrans2,
            greenAnimator
        )
        animatorSet.duration = Constant.ANIMATION_DELAY
        animatorSet.start()
    }

}