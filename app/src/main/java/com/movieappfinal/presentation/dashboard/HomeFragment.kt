package com.movieappfinal.presentation.dashboard

import android.os.Handler
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.movieappfinal.adapter.HomeCarouselAdapter
import com.movieappfinal.adapter.OnBoardingAdapter
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentHomeBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, AuthViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()
    private lateinit var viewPager: ViewPager2
    private lateinit var homeCarouselAdapter: HomeCarouselAdapter
    private lateinit var tabLayout: TabLayout


    override fun initView() {
        autoScrollOnBoarding()
        viewPager = binding.vpHomeMovie
        homeCarouselAdapter = HomeCarouselAdapter()
        viewPager.adapter = homeCarouselAdapter

        tabLayout = binding.tlMovie
        TabLayoutMediator(tabLayout, viewPager) {_,_ ->}.attach()
    }

    override fun initListener() {}

    override fun observeData() {}

    private fun autoScrollOnBoarding() {
        val handler = Handler()
        val update = kotlinx.coroutines.Runnable {
            if (viewPager.currentItem < homeCarouselAdapter.itemCount - 1) {
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