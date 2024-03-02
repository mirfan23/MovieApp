package com.movieappfinal.presentation.others

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.movieappfinal.R
import com.movieappfinal.adapter.CheckoutAdapter
import com.movieappfinal.core.domain.model.DataCheckout
import com.movieappfinal.core.domain.model.DataMoviePayment
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentCheckoutBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment :
    BaseFragment<FragmentCheckoutBinding, DashboardViewModel>(FragmentCheckoutBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private val checkoutAdapter by lazy {
        CheckoutAdapter {}
    }
    private val args: CheckoutFragmentArgs by navArgs()
    private val database = FirebaseDatabase.getInstance().reference
    private val tokenReference = database.child("token_transaction")
    private val movieReference = database.child("movie_transaction")
    var totalTokenAmount = 0
    var totalItemPrice = 0
    private var dataCheckoutToPayment = DataMoviePayment()

    override fun initView() {
        binding.toolbarCheckout.title = getString(R.string.check_out_title)
        binding.btnPay.text = getString(R.string.btn_buy)
        val dataCheckoutFragment = args.dataCheckout

        val user = viewModel.getCurrentUser()
        tokenReference.child(user?.userId ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for (transactionSnapshot in snapshot.children) {
                        val tokenAmount =
                            transactionSnapshot.child("tokenAmount").getValue(String::class.java)
                                ?.toIntOrNull() ?: 0
                        totalTokenAmount += tokenAmount
                    }
                    movieReference.child(user?.userId ?: "")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (transactionSnapshot in snapshot.children) {
                                    val itemPrice = transactionSnapshot.child("itemPrice")
                                        .getValue(Int::class.java) ?: 0
                                    totalItemPrice += itemPrice
                                }
                                val newTokenAmount = totalTokenAmount - totalItemPrice
                                binding.tvTokenBalance.text = newTokenAmount.toString()
                                binding.btnPay.setOnClickListener {
                                    if (newTokenAmount >= args.dataCheckout.itemPrice) {
                                        dataCheckoutToPayment = DataMoviePayment(
                                            movieId = args.dataCheckout.movieId,
                                            image = args.dataCheckout.image,
                                            itemName = args.dataCheckout.itemName,
                                            itemPrice = args.dataCheckout.itemPrice,
                                            totalPayment = newTokenAmount
                                        )
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

                            override fun onCancelled(error: DatabaseError) {
                                context?.let {
                                    CustomSnackbar.showSnackBar(
                                        it,
                                        binding.root,
                                        getString(R.string.failed_to_get_data)
                                    )
                                }
                            }
                        })
                    binding.tvTokenBalance.text = totalTokenAmount.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    context?.let {
                        CustomSnackbar.showSnackBar(
                            it,
                            binding.root,
                            getString(R.string.failed_to_get_data)
                        )
                    }
                }
            })
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