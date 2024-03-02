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
        tokenReference.child(user?.userId ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var totalTokenAmount = 0
                    for (transactionTokenSnapshot in snapshot.children) {
                        val tokenAmountString = transactionTokenSnapshot.child("tokenAmount").getValue(String::class.java)
                        val tokenAmount = tokenAmountString?.toIntOrNull() ?: 0

                        totalTokenAmount += tokenAmount
                    }
                    movieReference.child(user?.userId ?: "").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var totalItemPrice = 0
                            for (transactionMovieSnapshot in snapshot.children) {
                                val itemPrice = transactionMovieSnapshot.child("itemPrice")
                                    .getValue(Int::class.java) ?: 0
                                totalItemPrice += itemPrice
                            }
                            val newTokenAmount = totalTokenAmount - totalItemPrice

                            binding.tvTokenBalance.text = newTokenAmount.toString()
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
    }

    override fun initListener() {
        binding.toolbarMyToken.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnContinueToken.setOnClickListener {
            val enteredTokenAmount = binding.tietToken.text?.toString()?.toIntOrNull()
            if (enteredTokenAmount != null && selectedBottomSheetItem != null || viewModel.selectedItem.value != null) {
                val bundle = bundleOf(
                    "selectedTokenItem" to viewModel.selectedItem.value,
                    "enteredTokenAmount" to enteredTokenAmount,
                    "bottomSheetItem" to selectedBottomSheetItem
                )
                findNavController().navigate(
                    R.id.action_myTokenFragment_to_tokenPaymentFragment,
                    bundle
                )
//            if (viewModel.selectedItem.value != null && selectedBottomSheetItem != null ) {
//                val bundle = bundleOf(
//                    "selectedTokenItem" to viewModel.selectedItem.value,
//                    "bottomSheetItem" to selectedBottomSheetItem
//                )
//                findNavController().navigate(
//                    R.id.action_myTokenFragment_to_tokenPaymentFragment,
//                    bundle
//                )
            } else {
                context?.let { ctx ->
                    CustomSnackbar.showSnackBar(
                        ctx,
                        binding.root,
                        "Data tidak boleh kosong"
                    )
                }
            }
        }
        binding.cardPayment.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.listener = this
            bottomSheetFragment.show(childFragmentManager, BottomSheetFragment.TAG)
        }
        binding.tietToken.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                tokenPaymentAdapter.clearSelectedItem()
            }
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
        selectedBottomSheetItem = item
    }
}