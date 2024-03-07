package com.movieappfinal.presentation.others

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataCheckout
import com.movieappfinal.core.domain.model.DataDetailMovie
import com.movieappfinal.core.domain.model.DataListCheckout
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
    private var dataCheckout = DataCheckout()
    private var listDataCheckout = DataListCheckout()
    private val database = FirebaseDatabase.getInstance().reference
    private val movieReference = database.child("movie_transaction")

    override fun initView() {
        safeArgs.movieId.let { movieId ->
            viewModel.fetchDetail(movieId)
        }
        viewModel.getWishlistState()
        binding.apply {
            toolbarDetail.title = getString(R.string.detail_title)
            tvDetailProduct.text = getString(R.string.movie_overview)
            btnBuy.text = getString(R.string.rent_now_btn)
            btnToCart.text = getString(R.string.add_to_cart_btn)
        }
        updateWishlist()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
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
                binding.cbWishlist.setButtonDrawable(context?.getDrawable(R.drawable.ic_wishlist_filled))
                context?.let { context ->
                    CustomSnackbar.showSnackBar(
                        context,
                        binding.root,
                        getString(R.string.successful_added_to_wishlist)
                    )
                    viewModel.run { insertWishList() }
                }
            } else {
                binding.cbWishlist.setButtonDrawable(context?.getDrawable(R.drawable.ic_wishlist))
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
                        tvGenres.text =
                            it.genres.map { it.name }.toString().replace("[", "").replace("]", "");
                        tvDetailDesc.text = it.overview
                        tvMoviePrice.text = it.popularity.toString()
                        updateWishlist()
                        setDataCartDetail(it)
                        viewModel.setDataCart(
                            DataCart(
                                movieId = it.movieId,
                                image = Img_Url + it.poster,
                                movieTitle = it.title,
                                moviePrice = it.popularity,
                                userId = user.let { it?.userId ?: "" },
                                isChecked = false,
                            )
                        )
                        setDataWishlistDetail(it)
                        viewModel.setDataWishlist(
                            DataWishlist(
                                movieId = it.movieId,
                                image = Img_Url + it.poster,
                                movieTitle = it.title,
                                moviePrice = it.popularity,
                                userId = user.let { it?.userId ?: "" },
                                releaseDate = it.releaseDate,
                                wishlist = false
                            )
                        )
                        listDataCheckout = DataListCheckout(
                            listOf(
                                DataCheckout(
                                    movieId = it.movieId,
                                    image = Img_Url + it.poster,
                                    itemName = it.title,
                                    itemPrice = it.popularity

                                )
                            )
                        )
                        movieReference.child(user?.userId ?: "")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (childSnapshot in snapshot.children) {
                                        val firebaseMovieIds = childSnapshot.child("movieId").children.map { it.getValue(Int::class.java) ?: 0 }
                                        if (firebaseMovieIds.contains(it.movieId)) {
                                            binding.btnBuy.isEnabled = false
                                            binding.btnToCart.isEnabled = false
                                            break
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    println("MASUK GAGAL")
                                }
                            })
                        binding.btnBuy.setOnClickListener {
                            if (btnBuy.isEnabled) {
                                val bundle = bundleOf(
                                    "listDataCheckout" to listDataCheckout
                                )
                                findNavController().navigate(
                                    R.id.action_detailFragment_to_checkoutFragment,
                                    bundle
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateWishlist() {
        binding.cbWishlist.isChecked = viewModel.checkWishlist(safeArgs.movieId)
        when (binding.cbWishlist.isChecked) {
            true -> binding.cbWishlist.setButtonDrawable(context?.getDrawable(R.drawable.ic_wishlist_filled))
            else -> binding.cbWishlist.setButtonDrawable(context?.getDrawable(R.drawable.ic_wishlist))
        }
    }

    private fun setDataCartDetail(dataDetailMovie: DataDetailMovie) {
        val user = viewModel.getCurrentUser()
        DataCart(
            movieId = dataDetailMovie.movieId,
            image = Img_Url + dataDetailMovie.poster,
            movieTitle = dataDetailMovie.title,
            moviePrice = dataDetailMovie.popularity,
            userId = user.let { it?.userId ?: "" },
            isChecked = false,
        )
    }

    private fun setDataWishlistDetail(dataDetailMovie: DataDetailMovie) {
        val user = viewModel.getCurrentUser()
        DataWishlist(
            movieId = dataDetailMovie.movieId,
            image = Img_Url + dataDetailMovie.poster,
            movieTitle = dataDetailMovie.title,
            moviePrice = dataDetailMovie.popularity,
            userId = user.let { it?.userId ?: "" },
            releaseDate = dataDetailMovie.releaseDate,
            wishlist = false
        )
    }
}
