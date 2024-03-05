package com.movieappfinal.presentation.others

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieappfinal.R
import com.movieappfinal.adapter.CheckoutAdapter
import com.movieappfinal.core.domain.model.DataMoviePayment
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentCheckoutBinding
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment :
    BaseFragment<FragmentCheckoutBinding, DashboardViewModel>(FragmentCheckoutBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private val checkoutAdapter by lazy {
        CheckoutAdapter {}
    }
    private val args: CheckoutFragmentArgs by navArgs()
    private var totalToken = 0
    private var totalPrice = 0
    private var dataCheckoutToPayment = DataMoviePayment()


    override fun initView() {
        binding.toolbarCheckout.title = getString(R.string.check_out_title)
        binding.btnPay.text = getString(R.string.btn_buy)
        val dataCheckoutFragment = args.dataCheckout
        val listDataCheckout = args.listDataCheckout

        val dataCheckout = if ((dataCheckoutFragment == null) && (listDataCheckout != null)) {
            listDataCheckout
        } else {
            dataCheckoutFragment
        }

        val user = viewModel.getCurrentUser()
        viewModel.getTokenFromDatabase(user?.userId ?: "")
            .launchAndCollectIn(viewLifecycleOwner) { token ->
                totalToken = token
            }
        viewModel.getMovieTransactionFromDatabase(user?.userId ?: "")
            .launchAndCollectIn(viewLifecycleOwner) { transaction ->
                totalPrice = transaction

                val newTokenAmount = totalToken - totalPrice
                binding.tvTokenBalance.text = newTokenAmount.toString()
                binding.tvTotalPrice.text = totalPrice.toString()
                binding.btnPay.setOnClickListener {
                    if (newTokenAmount >= (args.dataCheckout?.itemPrice ?: 0)) {
                        dataCheckoutToPayment = DataMoviePayment(
                            movieId = args.dataCheckout?.movieId ?: 0,
                            image = args.dataCheckout?.image ?: "",
                            itemName = args.dataCheckout?.itemName ?: "",
                            itemPrice = args.dataCheckout?.itemPrice ?: 0,
                            totalPayment = newTokenAmount
                        )
                        /**
                         * commented code will be use later
                         */
//                    dataCheckout?.let {
//                        if (newTokenAmount >= it.itemPrice) {
//                            dataCheckoutToPayment = DataMoviePayment(
//                                movieId = checkout.movieId,
//                                image = checkout.image,
//                                itemName = checkout.itemName,
//                                itemPrice = checkout.itemPrice,
//                                totalPayment = newTokenAmount
//                            )
                            val bundle =
                                bundleOf("dataMoviePayment" to dataCheckoutToPayment)
                            findNavController().navigate(
                                R.id.action_checkoutFragment_to_moviePaymentPageFragment,
                                bundle
                            )
                        } else {
                            findNavController().navigate(R.id.action_checkoutFragment_to_myTokenFragment)
                        }
                    }
                }

                binding.rvListView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = checkoutAdapter
                    val listDataCheckout = listOf(dataCheckoutFragment)
                    checkoutAdapter.submitList(listDataCheckout)
                }
            }


    override fun initListener() {
        binding.toolbarCheckout.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observeData() {

    }
}
