package com.movieappfinal.presentation.others

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.database.FirebaseDatabase
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.domain.model.DataTokenTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentMoviePaymentPageBinding
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviePaymentPageFragment : BaseFragment<FragmentMoviePaymentPageBinding, DashboardViewModel>(FragmentMoviePaymentPageBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private val args: MoviePaymentPageFragmentArgs by navArgs()
    private val database = FirebaseDatabase.getInstance().reference
    override fun initView() {
        val dataPayment = args.dataCheckout
        binding.tvPrice.text = dataPayment.itemPrice.toString()
        binding.ivMoviePoster.load(dataPayment.image)
        binding.tvMovieTitle.text = dataPayment.itemName
        val userName = viewModel.getCurrentUser()
        binding.tvUsername.text = userName.let { it?.userName }

        binding.btnContinue.text = getString(R.string.btn_continue)
        binding.tvNameUser.text = getString(R.string.name_title_payment)
        binding.tvPriceTitle.text = getString(R.string.total_price_title)
    }

    override fun initListener() {}

    override fun observeData() {
        binding.btnContinue.setOnClickListener {
            saveTransactionToDatabase()
            findNavController().navigate(R.id.action_moviePaymentPageFragment_to_dashboardFragment)
        }
    }

    private fun saveTransactionToDatabase() {
        val itemPrice = args.dataCheckout.itemPrice
        val itemName = args.dataCheckout.itemName
        val tokenAmountNew = args.dataCheckout.totalAmount
        val userName = viewModel.getCurrentUser()

        val movieTransaction = DataMovieTransaction(
            itemName = itemName,
            itemPrice = itemPrice,
            totalAmount = tokenAmountNew,
            userName = userName?.userName ?: ""
        )
        /**
         * println() use as return so it still need it
         */
        database.child("movie_transaction").child(userName?.userName ?: "").push().setValue(movieTransaction)
            .addOnCompleteListener {
                println("MASUK : berhasil kirim ke database")
            }
            .addOnFailureListener {
                println("MASUK: GAGAL COK")
            }
    }

}