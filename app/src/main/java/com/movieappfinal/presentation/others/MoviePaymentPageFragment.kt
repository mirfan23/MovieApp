package com.movieappfinal.presentation.others

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.database.FirebaseDatabase
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentMoviePaymentPageBinding
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MoviePaymentPageFragment : BaseFragment<FragmentMoviePaymentPageBinding, DashboardViewModel>(FragmentMoviePaymentPageBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private val args: MoviePaymentPageFragmentArgs by navArgs()
    private val database = FirebaseDatabase.getInstance().reference
    override fun initView() {
        val dataPayment = args.dataMoviePayment
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun observeData() {
        binding.btnContinue.setOnClickListener {
            saveTransactionToDatabase()
            findNavController().navigate(R.id.action_moviePaymentPageFragment_to_dashboardFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveTransactionToDatabase() {
        val movieId = args.dataMoviePayment.movieId
        val itemPrice = args.dataMoviePayment.itemPrice
        val itemName = args.dataMoviePayment.itemName
        val totalPrice = args.dataMoviePayment.totalPayment
        val userName = viewModel.getCurrentUser()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val transactionDateTime = LocalDateTime.now().format(formatter)

        val movieTransaction = DataMovieTransaction(
            userId = userName?.userId ?: "",
            userName = userName?.userName ?: "",
            movieId = movieId,
            itemName = itemName,
            itemPrice = itemPrice,
            totalPrice = totalPrice,
            transactionTime = transactionDateTime.toString()
        )
        /**
         * println() use as return so it still need it
         */
        database.child("movie_transaction").child(userName?.userId ?: "").push().setValue(movieTransaction)
            .addOnCompleteListener {
                println("MASUK : berhasil kirim ke database")
            }
            .addOnFailureListener {
                println("MASUK: GAGAL COK")
            }
    }

}