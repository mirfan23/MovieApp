package com.movieappfinal.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.movieappfinal.R
import com.movieappfinal.databinding.CustomToastBinding

object CustomSnackbar {

    @SuppressLint("RestrictedApi")
    fun showSnackBar(context: Context, v: View, text: String) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = CustomToastBinding.inflate(inflater)
        val snackbar = Snackbar.make(v, "", Snackbar.LENGTH_INDEFINITE)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

        binding.tvSnackbar.text = text

        snackbarLayout.apply {
            val laypar = layoutParams as FrameLayout.LayoutParams
            laypar.gravity = Gravity.TOP
            layoutParams = laypar

            addView(binding.root, 0)
        }

        snackbar.apply {
            view.setBackgroundColor(Color.TRANSPARENT)
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

            val slideDownAnim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            val slideUpAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up)

            view.startAnimation(slideDownAnim)

            slideDownAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    view.startAnimation(slideUpAnim)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })

            slideUpAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    view.visibleIf(false)
                    dismiss()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            show()
        }
    }
}