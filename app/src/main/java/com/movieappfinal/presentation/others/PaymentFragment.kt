package com.movieappfinal.presentation.others

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataTokenTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentPaymentBinding
import com.movieappfinal.utils.currency
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PaymentFragment :
    BaseFragment<FragmentPaymentBinding, HomeViewModel>(FragmentPaymentBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private val args: PaymentFragmentArgs by navArgs()

    override fun initView() {
        val selectedTokenItem = args.selectedTokenItem
        val bottomSheetItem = args.bottomSheetItem
        val enteredTokenAmount = args.enteredTokenAmount
        binding.tvNameUser.text = getString(R.string.name_title_payment)
        binding.tvPaymentMethodTitle.text = getString(R.string.payment_method_title)
        binding.btnContinue.text = getString(R.string.btn_continue)
        binding.tvPriceTitle.text = getString(R.string.total_price_title)

        binding.tvPaymentMethod.text = bottomSheetItem.label
        val userName = viewModel.getCurrentUser()
        binding.tvUsername.text = userName.let { it?.userName }

        if (selectedTokenItem != null) {
            binding.tvTokenTopUp.text = selectedTokenItem.token.toString()
            binding.tvPrice.text = currency(selectedTokenItem.price)
        } else {
            binding.tvTokenTopUp.text = enteredTokenAmount.toString()
            val totalPrice = enteredTokenAmount * 150
            binding.tvPrice.text = currency(totalPrice)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initListener() {
        binding.btnContinue.setOnClickListener {
            saveTransactionToDatabase()
        }
    }

    override fun observeData() {}

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveTransactionToDatabase() {
        val bottomSheetItem = args.bottomSheetItem.label
        val userName = viewModel.getCurrentUser()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val transactionDateTime = LocalDateTime.now().format(formatter)

        val selectedTokenItem = if (args.selectedTokenItem != null) {
            args.selectedTokenItem?.token
        } else {
            args.enteredTokenAmount
        }

        val itemPrice = if (args.selectedTokenItem != null) {
            args.selectedTokenItem?.price
        } else {
            args.enteredTokenAmount * 150
        }

        val tokenTransaction = DataTokenTransaction(
            userId = userName?.userId ?: "",
            tokenAmount = selectedTokenItem.toString(),
            tokenPrice = itemPrice.toString(),
            paymentMethod = bottomSheetItem,
            userName = userName?.userName ?: "",
            transactionTime = transactionDateTime.toString()
        )
        viewModel.sendTokenToDatabase(data = tokenTransaction, userId = userName?.userId ?: "")
            .launchAndCollectIn(viewLifecycleOwner) { success ->
                if (success) {
                    findNavController().navigate(R.id.action_tokenPaymentFragment_to_dashboardFragment)
                }
            }
    }
}
