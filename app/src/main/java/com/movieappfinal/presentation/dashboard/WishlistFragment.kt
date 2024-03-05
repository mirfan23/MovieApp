package com.movieappfinal.presentation.dashboard

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieappfinal.R
import com.movieappfinal.adapter.WishlistAdapter
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.core.domain.state.onSuccess
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentWishlistBinding
import com.movieappfinal.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishlistFragment :
    BaseFragment<FragmentWishlistBinding, HomeViewModel>(FragmentWishlistBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private var dataWishList: List<DataWishlist>? = null
    private val wishlistAdapter by lazy {
        WishlistAdapter(
            action = {
                /**
                 * will use it later
                 */
                val bundle = bundleOf("movieId" to it.movieId)
                activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                    ?.findNavController()
                    ?.navigate(R.id.action_dashboardFragment_to_detailFragment, bundle)
            },
            remove = { removeItemFromWishlist(it) }
        )
    }

    override fun initView() {
        listView()
        viewModel.fetchWishList()
    }

    override fun initListener() {}

    override fun observeData() {
        with(viewModel) {
            fetchWishList().launchAndCollectIn(viewLifecycleOwner) { wishListState ->
                this.launch {
                    wishListState.onSuccess { data ->
                        dataWishList = data
                        wishlistAdapter.submitList(data)
                        binding.tvTotalWishItem.text =
                            getString(R.string.total_items).replace("%total%", data.size.toString())
                    }
                }
            }
        }
    }

    private fun removeItemFromWishlist(dataWishlist: DataWishlist) {
        viewModel.removeWishlist(dataWishlist)
    }

    private fun listView() {
        binding.rvListView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = wishlistAdapter
        }
    }
}
