package com.movieappfinal.presentation.others

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.core.domain.model.DataPaymentMethod
import com.movieappfinal.core.domain.model.DataPaymentMethodItem
import com.movieappfinal.R
import com.movieappfinal.adapter.TokenPaymentAdapter
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
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
    private var totalToken = 0
    private var totalPrice = 0

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
        viewModel.getTokenFromDatabase(user?.userId ?: "")
            .launchAndCollectIn(viewLifecycleOwner) { token ->
                totalToken = token
            }
        viewModel.getMovieTransactionFromDatabase(user?.userId ?: "")
            .launchAndCollectIn(viewLifecycleOwner) { transaction ->
                totalPrice = transaction

                val newTokenAmount = totalToken - totalPrice
                binding.tvTokenBalance.text = newTokenAmount.toString()
            }
    }

    override fun initListener() {
        binding.toolbarMyToken.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnContinueToken.setOnClickListener {
            val tokenAmountToSend = binding.tietToken.text?.toString()?.toIntOrNull()
                ?: viewModel.selectedItem.value?.token
            if (tokenAmountToSend != null && selectedBottomSheetItem != null || viewModel.selectedItem.value != null) {
                val bundle = bundleOf(
                    "selectedTokenItem" to viewModel.selectedItem.value,
                    "enteredTokenAmount" to tokenAmountToSend,
                    "bottomSheetItem" to selectedBottomSheetItem
                )
                findNavController().navigate(
                    R.id.action_myTokenFragment_to_tokenPaymentFragment,
                    bundle
                )
            } else {
                context?.let { ctx ->
                    CustomSnackbar.showSnackBar(
                        ctx,
                        binding.root,
                        getString(R.string.data_con_not_be_null)
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
