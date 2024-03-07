package com.movieappfinal.presentation.others

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieappfinal.R
import com.movieappfinal.adapter.CheckoutAdapter
import com.movieappfinal.core.domain.model.DataCheckout
import com.movieappfinal.core.domain.model.DataListCheckout
import com.movieappfinal.core.domain.model.DataListMovie
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
    private var listDataCheckoutToPayment: DataListMovie? = null
    private var listDataCheckout: DataListCheckout? = null


    override fun initView() {
        listDataCheckout = args.listDataCheckout
        binding.toolbarCheckout.title = getString(R.string.check_out_title)
        binding.btnPay.text = getString(R.string.btn_buy)

        binding.rvListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = checkoutAdapter
            checkoutAdapter.submitList(listDataCheckout?.listDataCheckout)
        }
    }


    override fun initListener() {
        binding.toolbarCheckout.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observeData() {
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
                val totalItemPrice =
                    listDataCheckout?.listDataCheckout?.sumOf { it.itemPrice ?: 0 } ?: 0
                binding.tvTotalPrice.text = totalItemPrice.toString()
                binding.btnPay.setOnClickListener {
                    val dataCheckoutList =
                        listDataCheckout?.listDataCheckout


                    if (newTokenAmount >= totalItemPrice) {
                        listDataCheckoutToPayment =
                            DataListMovie(dataCheckoutList?.map { dataCheckout ->
                                DataMoviePayment(
                                    movieId = dataCheckout.movieId ?: 0,
                                    image = dataCheckout.image ?: "",
                                    itemName = dataCheckout.itemName ?: "",
                                    itemPrice = dataCheckout.itemPrice ?: 0,
                                )
                            } ?: listOf(),
                                totalPayment = totalItemPrice
                            )
                        val bundle = bundleOf("listDataMoviePayment" to listDataCheckoutToPayment)
                        findNavController().navigate(
                            R.id.action_checkoutFragment_to_moviePaymentPageFragment,
                            bundle
                        )
                    } else {
                        findNavController().navigate(R.id.action_checkoutFragment_to_myTokenFragment)
                    }
                }
            }
    }
}
