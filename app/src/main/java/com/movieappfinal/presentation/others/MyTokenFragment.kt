package com.movieappfinal.presentation.others

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.movieappfinal.R
import com.movieappfinal.adapter.TokenPaymentAdapter
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentMyTokenBinding
import com.movieappfinal.utils.SpaceItemDecoration
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyTokenFragment : BaseFragment<FragmentMyTokenBinding, DashboardViewModel>(FragmentMyTokenBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private val list: MutableList<DataTokenPaymentItem> = mutableListOf()
    private val tokenPaymentAdapter by lazy {
        TokenPaymentAdapter{ item ->
            viewModel.setSelectedItem(item)
        }
    }


    override fun initView()  {
        binding.apply {
            toolbarMyToken.title = getString(R.string.my_token_title)
            tvBalanceTitle.text = getString(R.string.balance_title)
            btnContinueToken.text = getString(R.string.btn_continue)
            tietToken.hint = getString(R.string.token_balance)
            tvPaymentWay.text = getString(R.string.choose_payment)
            rvListToken.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = tokenPaymentAdapter
            val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
            addItemDecoration(SpaceItemDecoration(spaceInPixels))
            }
        }
    }

    override fun initListener() {
        binding.toolbarMyToken.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnContinueToken.setOnClickListener {
            viewModel.selectedItem.value?.let { selectedItem ->
                println("MASUK : $selectedItem")
            }
        }
        binding.cardPayment.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
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
}