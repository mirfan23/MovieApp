package com.movieappfinal.presentation.others

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.movieappfinal.R
import com.movieappfinal.adapter.MoviePaymentItemAdapter
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentMoviePaymentPageBinding
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MoviePaymentPageFragment : BaseFragment<FragmentMoviePaymentPageBinding, DashboardViewModel>(
    FragmentMoviePaymentPageBinding::inflate
) {
    override val viewModel: DashboardViewModel by viewModel()
    private val args: MoviePaymentPageFragmentArgs by navArgs()
    private val paymentMovieItemAdapter by lazy {
        MoviePaymentItemAdapter {}
    }

    override fun initView() {
        val userName = viewModel.getCurrentUser()
        val dataPayment = args.listDataMoviePayment.listDataMovie
        binding.apply {
            tvPrice.text = args.listDataMoviePayment.totalPayment.toString()
            tvUsername.text = userName.let { it?.userName }
            btnContinue.text = getString(R.string.btn_continue)
            tvNameUser.text = getString(R.string.name_title_payment)
            tvPriceTitle.text = getString(R.string.total_price_title)
            rvMoviePaymentItem.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = paymentMovieItemAdapter
                paymentMovieItemAdapter.submitList(dataPayment)
            }
        }


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
        val movieId = args.listDataMoviePayment.listDataMovie.map { it.movieId }
        val itemPrice = args.listDataMoviePayment.listDataMovie.map { it.itemPrice }
        val itemName = args.listDataMoviePayment.listDataMovie.map { it.itemName }
        val totalPrice = args.listDataMoviePayment.totalPayment
        val userName = viewModel.getCurrentUser()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
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
        viewModel.sendMovieToDatabase(
            dataMovieTransaction = movieTransaction,
            userId = userName?.userId ?: ""
        )
            .launchAndCollectIn(viewLifecycleOwner) { success ->
                if (success) {
                    findNavController().navigate(R.id.action_tokenPaymentFragment_to_dashboardFragment)
                }
            }
    }

}
