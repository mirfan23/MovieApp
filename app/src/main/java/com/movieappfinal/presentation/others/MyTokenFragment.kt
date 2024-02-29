package com.movieappfinal.presentation.others

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.core.domain.model.DataPaymentMethod
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.movieappfinal.core.domain.model.DataPaymentMethodItem
import com.movieappfinal.R
import com.movieappfinal.adapter.TokenPaymentAdapter
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.core.domain.model.DataTokenTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentMyTokenBinding
import com.movieappfinal.presentation.others.BottomSheetFragment.Companion.ARG_SELECTED_PAYMENT
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpaceItemDecoration
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyTokenFragment :
    BaseFragment<FragmentMyTokenBinding, DashboardViewModel>(FragmentMyTokenBinding::inflate),
    BottomSheetFragment.BottomSheetListener {
    override val viewModel: DashboardViewModel by viewModel()
    private val list: MutableList<DataTokenPaymentItem> = mutableListOf()
    private val tokenPaymentAdapter by lazy {
        TokenPaymentAdapter { item ->
            viewModel.setSelectedItem(item)
        }
    }
    private val selectedPayment: DataPaymentMethod? by lazy {
        arguments?.getParcelable(ARG_SELECTED_PAYMENT) as DataPaymentMethod?
    }
    private var selectedBottomSheetItem: DataPaymentMethodItem? = null
    private val database = FirebaseDatabase.getInstance().reference
    private val tokenReference = database.child("token_transaction")
    private val movieReference = database.child("movie_transaction")


    override fun initView() {
        binding.apply {
            toolbarMyToken.title = getString(R.string.my_token_title)
            tvBalanceTitle.text = getString(R.string.balance_title)
            btnContinueToken.text = getString(R.string.btn_continue)
            tietToken.hint = getString(R.string.token_balance)
            tvPaymentWay.text = getString(R.string.choose_payment)
            tvTokenBalance.text = ""
            rvListToken.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = tokenPaymentAdapter
                val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
                addItemDecoration(SpaceItemDecoration(spaceInPixels))
            }
            selectedPayment?.let { payment ->
                ivThumbnailCart.load(payment.item)
            }
        }
        val user = viewModel.getCurrentUser()
        tokenReference.child(user?.userName ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var totalTokenAmount = 0
                    for (transactionSnapshot in snapshot.children) {
                        val tokenAmount =
                            transactionSnapshot.child("tokenAmount").getValue(String::class.java)
                                ?.toInt() ?: 0
                        totalTokenAmount += tokenAmount
                    }
                    movieReference.child(user?.userName ?: "").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var totalItemPrice = 0
                            for (transactionSnapshot in snapshot.children) {
                                val itemPrice = transactionSnapshot.child("itemPrice")
                                    .getValue(Int::class.java) ?: 0
                                totalItemPrice += itemPrice
                                println("MASUK snapshot $snapshot")
                                println("MASUK itemPrice: $itemPrice")
                            }
                            println("MASUK totalPrice: $totalItemPrice")
                            val newTokenAmount = totalTokenAmount - totalItemPrice
                            println("MASUK: $newTokenAmount")

                            binding.tvTokenBalance.text = newTokenAmount.toString()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            context?.let {
                                CustomSnackbar.showSnackBar(
                                    it,
                                    binding.root,
                                    "Gagal mengambil data"
                                )
                            }
                        }
                    })
                }

                override fun onCancelled(error: DatabaseError) {
                    context?.let {
                        CustomSnackbar.showSnackBar(
                            it,
                            binding.root,
                            "Gagal mengambil data"
                        )
                    }
                }
            })
    }

    override fun initListener() {
        binding.toolbarMyToken.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnContinueToken.setOnClickListener {
            val bundle = bundleOf(
                "selectedTokenItem" to viewModel.selectedItem.value,
                "bottomSheetItem" to selectedBottomSheetItem
            )
            findNavController().navigate(
                R.id.action_myTokenFragment_to_tokenPaymentFragment,
                bundle
            )
            println("MASUK: $bundle")
        }
        binding.cardPayment.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.listener = this
            bottomSheetFragment.show(childFragmentManager, BottomSheetFragment.TAG)
        }
    }

    override fun observeData() {
        with(viewModel) {
            getConfigStatusUpdate().launchAndCollectIn(viewLifecycleOwner) {
                getDataPayment()
            }
            getDataPayment()
        }
    }

    private fun getDataPayment() {
        viewModel.doPayment().launchAndCollectIn(viewLifecycleOwner) {
            list.addAll(it)
            tokenPaymentAdapter.submitList(list)
        }
    }

    override fun onItemSelected(item: DataPaymentMethodItem) {
        binding.ivThumbnailCart.load(item.image)
        binding.tvPaymentWay.text = item.label
        println("MASUK: $item")
        selectedBottomSheetItem = item
    }
}