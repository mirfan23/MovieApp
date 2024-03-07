package com.movieappfinal.presentation.others

import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.movieappfinal.R
import com.movieappfinal.adapter.CartAdapter
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataCheckout
import com.movieappfinal.core.domain.model.DataListCheckout
import com.movieappfinal.core.domain.state.oError
import com.movieappfinal.core.domain.state.onLoading
import com.movieappfinal.core.domain.state.onSuccess
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentCartBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpaceItemDecoration
import com.movieappfinal.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class CartFragment :
    BaseFragment<FragmentCartBinding, HomeViewModel>(FragmentCartBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private var listDataCheckout = DataListCheckout()

    private val cartAdapter by lazy {
        CartAdapter(
            action = {
                val bundle = bundleOf("movieId" to it.movieId)
                findNavController().navigate(R.id.action_cartFragment_to_detailFragment, bundle)
            },
            remove = { entity -> removeItemFromCart(entity) },
            checkbox = { id, isChecked ->
                viewModel.updateCheckCart(id, isChecked)
                android.os.Handler(Looper.getMainLooper())
                    .postDelayed({ viewModel.updateTotalPrice() }, 500L)
            }
        )
    }

    override fun initView() {
        listView()
        viewModel.fetchCart()
        binding.apply {
            cartToolbar.title = getString(R.string.cart_title)
            tvTotalPaymentCartTitle.text = getString(R.string.total_price_title)
            btnPay.text = getString(R.string.btn_buy)
        }
        binding.cbSelectAll.setOnClickListener {
            val isChecked = binding.cbSelectAll.isChecked
            cartAdapter.setAllChecked(isChecked)
        }
    }

    override fun initListener() {
        binding.cartToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnPay.setOnClickListener {
            viewModel.retrieveCheckedCart()
            val bundle =
                bundleOf(
                    "listDataCheckout" to listDataCheckout,
                )
            findNavController().navigate(
                R.id.action_cartFragment_to_checkoutFragment,
                bundle
            )
        }
    }

    override fun observeData() {
        with(viewModel) {
            fetchCart().launchAndCollectIn(viewLifecycleOwner) { cartState ->
                this.launch {
                    cartState.onLoading {}.oError { error ->
                        val errorMessage = when (error) {
                            is HttpException -> {
                                val errorBody = error.response()?.errorBody()?.string()
                                "$errorBody"
                            }

                            else -> "${error.message}"
                        }
                        context?.let {
                            CustomSnackbar.showSnackBar(
                                it,
                                binding.root,
                                errorMessage
                            )
                        }
                    }.onSuccess { data ->
                        cartAdapter.submitList(data)
                    }
                }
            }
            retrieveCheckedCart().launchAndCollectIn(viewLifecycleOwner) { state ->
                this.launch {
                    state.onLoading {}
                        .oError { error ->
                            val errorMessage = when (error) {
                                is HttpException -> {
                                    val errorBody = error.response()?.errorBody()?.string()
                                    "$errorBody"
                                }

                                else -> "${error.message}"
                            }
                            context?.let {
                                CustomSnackbar.showSnackBar(
                                    it,
                                    binding.root,
                                    errorMessage
                                )
                            }
                        }.onSuccess { data ->
                            viewModel.setDataListCart(data)
                            listDataCheckout = DataListCheckout(
                                data.map {
                                    DataCheckout(
                                        movieId = it.movieId,
                                        image = it.image,
                                        itemPrice = it.moviePrice,
                                        itemName = it.movieTitle
                                    )
                                }
                            )
                        }
                }

            }
            totalPrice.launchAndCollectIn(viewLifecycleOwner) {
                binding.tvTotalPrice.text = it.toString()
            }
        }
    }

    private fun removeItemFromCart(dataCart: DataCart) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.are_you_sure))
                .setMessage(getString(R.string.item_will_deleted))
                .setNegativeButton(getString(R.string.cancel_btn_cart)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(getString(R.string.delete_btn_cart)) { _, _ ->
                    removeCartItem(dataCart)
                }
                .show()
                .getButton(AlertDialog.BUTTON_POSITIVE)
                ?.setTextColor(ContextCompat.getColor(it, R.color.error))
        }
    }

    private fun listView() {
        binding.rvListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
            val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
            addItemDecoration(SpaceItemDecoration(spaceInPixels))
        }
    }

    private fun removeCartItem(dataCart: DataCart) {
        viewModel.removeCart(dataCart)
    }

}
