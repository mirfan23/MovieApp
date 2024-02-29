package com.movieappfinal.presentation.others

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataCheckout
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.core.domain.state.onSuccess
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentDetailBinding
import com.movieappfinal.utils.Constant.Img_Url
import com.movieappfinal.utils.Constant.Img_Url_Original
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment :
    BaseFragment<FragmentDetailBinding, HomeViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private val safeArgs: DetailFragmentArgs by navArgs()
    private var dataCart = DataCart()
    private var dataWishlist = DataWishlist()
    private var dataCheckout = DataCheckout()

    override fun initView() {
        safeArgs.movieId.let { movieId ->
            viewModel.fetchDetail(movieId)
        }
        binding.apply {
            toolbarDetail.title = getString(R.string.detail_title)
            tvDetailProduct.text = getString(R.string.movie_overview)
            btnBuy.text = getString(R.string.rent_now_btn)
            btnToCart.text = getString(R.string.add_to_cart_btn)
        }
    }

    override fun initListener() {
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnToCart.setOnClickListener {
            viewModel.insertCart()
            context?.let { context ->
                CustomSnackbar.showSnackBar(
                    context,
                    binding.root,
                    getString(R.string.successful_added_to_cart)
                )
            }
        }
        binding.cbWishlist.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.run { insertWishList() }
                context?.let { context ->
                    CustomSnackbar.showSnackBar(
                        context,
                        binding.root,
                        getString(R.string.successful_added_to_wishlist)
                    )
                }
            } else {
                viewModel.run { removeWishlistDetail() }
                context?.let { context ->
                    CustomSnackbar.showSnackBar(
                        context,
                        binding.root,
                        getString(R.string.success_delete_from_wishlist)
                    )
                }
            }
            viewModel.putWishlistState(isChecked)
        }
    }

    override fun observeData() {
        with(viewModel) {
            responseDetail.launchAndCollectIn(viewLifecycleOwner) { detailState ->
                detailState.onSuccess {
                    binding.apply {
                        val user = viewModel.getCurrentUser()
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
                        tvMoviePrice.text = it.popularity.toString()
                        dataCart = DataCart(
                            movieId = it.id,
                            image = Img_Url+it.poster,
                            movieTitle = it.title,
                            moviePrice = it.popularity,
                            userId = user.let { it?.userId.hashCode() },
                            isChecked = false,
                        )
                        viewModel.setDataCart(dataCart)
                        dataWishlist = DataWishlist(
                            movieId = it.id,
                            image = Img_Url+it.poster,
                            movieTitle = it.title,
                            moviePrice = it.popularity,
                            userId = user.let { it?.userId.hashCode() },
                            releaseDate = it.releaseDate,
                            wishlist = false
                        )
                        viewModel.setDataWishlist(dataWishlist)
                        dataCheckout = DataCheckout(
                            image = Img_Url+it.poster,
                            itemName = it.title,
                            itemPrice = it.popularity
                        )
                        binding.btnBuy.setOnClickListener {
                            val bundle = bundleOf("dataCheckout" to dataCheckout)
                            findNavController().navigate(R.id.action_detailFragment_to_checkoutFragment, bundle)
                        }
                    }
                }
            }
        }
    }
}