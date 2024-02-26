package com.movieappfinal.presentation.dashboard

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.movieappfinal.adapter.HomeAdapter
import com.movieappfinal.adapter.HomeCarouselAdapter
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.core.domain.state.oError
import com.movieappfinal.core.domain.state.onLoading
import com.movieappfinal.core.domain.state.onSuccess
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentHomeBinding
import com.movieappfinal.utils.Constant.Img_Url
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val list: MutableList<DataPopularMovie> = mutableListOf()
    private val list2: MutableList<DataPopularMovieItem> = mutableListOf()
    private val homeAdapter by lazy {
        HomeAdapter(action = {})
    }
    private val homeCarouselAdapter by lazy {
        HomeCarouselAdapter()
    }


    override fun initView() {
        autoScrollOnBoarding()
        viewModel.fetchPopularMovie()
        viewModel.fetchNowPlaying()

        tabLayout = binding.tlMovie
        viewPager = binding.vpHomeMovie
        viewPager.adapter = homeCarouselAdapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        binding.rvHomeItem.run {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            hasFixedSize()
        }
    }

    override fun initListener() {}

    override fun observeData() {
        with(viewModel) {
            responsePopularMovie.launchAndCollectIn(viewLifecycleOwner) { movieState ->
                movieState.onSuccess {
                    list.addAll(listOf(it))
                    homeAdapter.submitList(list)
                }.oError {
                    context?.let { ctx ->
                        CustomSnackbar.showSnackBar(
                            ctx,
                            binding.root,
                            "Gagal Mendapatkan Data"
                        )
                    }
                }.onLoading {}

            }
        }
    }

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

//    private fun setCarouselImage(image: List<String>, title: List<String>) {
//        homeCarouselAdapter = homeCarouselAdapter
//        tabLayout = binding.tlMovie
//        viewPager = binding.vpHomeMovie
//        viewPager.adapter = homeCarouselAdapter
//
//        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
//    }
}