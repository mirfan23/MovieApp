package com.movieappfinal.presentation.others

import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.movieappfinal.R
import com.movieappfinal.adapter.CartAdapter
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.state.oError
import com.movieappfinal.core.domain.state.onLoading
import com.movieappfinal.core.domain.state.onSuccess
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentCartBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class CartFragment :
    BaseFragment<FragmentCartBinding, HomeViewModel>(FragmentCartBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()

    private val cartAdapter by lazy {
        CartAdapter(
            action = {

            },
            remove = { entity -> removeItemFromCart(entity) },
            checkbox = { id, isChecked -> }
        )
    }

    override fun initView() {
        listView()
        viewModel.fetchCart()
        binding.apply {
            cartToolbar.title = getString(R.string.cart_title)
            btnDeleteCart.text = getString(R.string.delete_btn_text)
        }
    }

    override fun initListener() {
        binding.cartToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
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
                        viewModel.setDataListCart(data)
                    }
                }
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
        }
    }

    private fun removeCartItem(dataCart: DataCart) {
        viewModel.removeCart(dataCart)
    }

}