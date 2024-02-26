package com.movieappfinal.presentation.others

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.movieappfinal.R
import com.movieappfinal.core.domain.state.onSuccess
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentDetailBinding
import com.movieappfinal.utils.Constant.Img_Url
import com.movieappfinal.utils.Constant.Img_Url_Original
import com.movieappfinal.viewmodel.AuthViewModel
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment :
    BaseFragment<FragmentDetailBinding, HomeViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun initView() = with(viewModel) {
        safeArgs.movieId.let { movieId ->
            fetchDetail(movieId)
        }
        binding.toolbarDetail.title = "Detail"
    }

    override fun initListener() {
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observeData() {
        with(viewModel) {
            responseDetail.launchAndCollectIn(viewLifecycleOwner) { detailState ->
                detailState.onSuccess {
                    binding.apply {
                        ivMovieBg.load(Img_Url_Original + it.backdrop)
                        ivMoviePoster.load(Img_Url + it.poster)
                        tvMovieTitleDetail.text = it.title
                        tvReleaseDate.text = it.releaseDate
                        tvDuration.text = getString(R.string.duration_minutes).replace(
                            "%duration%",
                            it.runtime.toString()
                        )
                        tvGenres.text = it.genres.map { it.name }.toString()
                        tvDetailDesc.text = it.overview
                    }
                }
            }
        }
    }

}