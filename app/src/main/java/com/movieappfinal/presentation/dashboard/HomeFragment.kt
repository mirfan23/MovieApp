package com.movieappfinal.presentation.dashboard

import android.os.Bundle
import android.os.Handler
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.movieappfinal.R
import com.movieappfinal.adapter.HomePopularAdapter
import com.movieappfinal.adapter.HomeCarouselAdapter
import com.movieappfinal.adapter.HomeNowPlayingAdapter
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.core.domain.state.oError
import com.movieappfinal.core.domain.state.onLoading
import com.movieappfinal.core.domain.state.onSuccess
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentHomeBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val listPopular: MutableList<DataPopularMovie> = mutableListOf()
    private val listNowPlaying: MutableList<DataNowPlaying> = mutableListOf()
    private val listCarousel: MutableList<DataPopularMovieItem> = mutableListOf()
    private val homePopularAdapter by lazy {
        HomePopularAdapter { data ->
            val bundle = bundleOf("movieId" to data.id)
            activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                ?.findNavController()
                ?.navigate(R.id.action_dashboardFragment_to_detailFragment, bundle)
        }
    }
    private val homeNowPlayingAdapter by lazy {
        HomeNowPlayingAdapter{ data ->
            val bundle = bundleOf("movieId" to data.id)
            activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                ?.findNavController()
                ?.navigate(R.id.action_dashboardFragment_to_detailFragment, bundle)
        }
    }
    private val homeCarouselAdapter by lazy {
        HomeCarouselAdapter(listCarousel)
    }


    override fun initView() {
        autoScrollOnBoarding()
        viewModel.fetchPopularMovie()
        viewModel.fetchNowPlaying()

        tabLayout = binding.tlMovie
        viewPager = binding.vpHomeMovie



        binding.rvHomePopular.run {
            layoutManager = LinearLayoutManager(context)
            adapter = homePopularAdapter
            hasFixedSize()
        }
        binding.rvHomeNowPlaying.run {
            layoutManager = LinearLayoutManager(context)
            adapter = homeNowPlayingAdapter
            hasFixedSize()
        }
    }

    override fun initListener() {}

    override fun observeData() {
        with(viewModel) {
            responsePopularMovie.launchAndCollectIn(viewLifecycleOwner) { movieState ->
                movieState.onSuccess {
                    listPopular.addAll(listOf(it))
                    homePopularAdapter.submitList(listPopular)
                    binding.vpHomeMovie.adapter = HomeCarouselAdapter(it.itemsPopular)
                    TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
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
            responseNowPlaying.launchAndCollectIn(viewLifecycleOwner) { movieState ->
                movieState.onSuccess {
                    listNowPlaying.addAll(listOf(it))
                    homeNowPlayingAdapter.submitList(listNowPlaying)
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
        val delay = 3000L

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