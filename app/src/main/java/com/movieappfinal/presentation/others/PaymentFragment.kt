package com.movieappfinal.presentation.others

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.FirebaseDatabase
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataTokenTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentPaymentBinding
import com.movieappfinal.utils.currency
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentFragment : BaseFragment<FragmentPaymentBinding, HomeViewModel>(FragmentPaymentBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private val args: PaymentFragmentArgs by navArgs()
    private val database = FirebaseDatabase.getInstance().reference

    override fun initView() {
        val selectedTokenItem = args.selectedTokenItem
        val bottomSheetItem = args.bottomSheetItem
        binding.tvNameUser.text = getString(R.string.name_title_payment)
        binding.tvPaymentMethodTitle.text = getString(R.string.payment_method_title)
        binding.btnContinue.text = getString(R.string.btn_continue)
        binding.tvPriceTitle.text = getString(R.string.total_price_title)

        binding.tvTokenTopUp.text = selectedTokenItem.token.toString()
        binding.tvPrice.text = currency(selectedTokenItem.price)
        binding.tvPaymentMethod.text = bottomSheetItem.label
        val userName = viewModel.getCurrentUser()
        binding.tvUsername.text = userName.let { it?.userName }

    }

    override fun initListener() {
        binding.btnContinue.setOnClickListener {
            saveTransactionToDatabase()
            findNavController().navigate(R.id.action_tokenPaymentFragment_to_dashboardFragment)
        }
    }

    override fun observeData() {}

    private fun saveTransactionToDatabase() {
        val selectedTokenItem = args.selectedTokenItem.token.toString()
        val itemPrice = args.selectedTokenItem.price
        val bottomSheetItem = args.bottomSheetItem.label
        val userName = viewModel.getCurrentUser()

        val tokenTransaction = DataTokenTransaction(
            tokenAmount = selectedTokenItem,
            tokenPrice = itemPrice.toString(),
            paymentMethod = bottomSheetItem,
            userName = userName?.userName ?: ""
        )
        /**
         * println() use as return so it still need it
         */
        database.child("token_transaction").child(userName?.userName ?: "").push().setValue(tokenTransaction)
            .addOnCompleteListener {
                println("MASUK : berhasil kirim ke database")
            }
            .addOnFailureListener {
                println("MASUK: GAGAL COK")
            }
    }
}