package com.movieappfinal.presentation.dashboard

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.movieappfinal.R
import com.movieappfinal.adapter.TransactionAdapter
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentTransactionBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpaceItemDecoration
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding, DashboardViewModel>(FragmentTransactionBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private val database = FirebaseDatabase.getInstance().reference
    private val movieReference = database.child("movie_transaction")
    private val movieTransactionItem: MutableList<DataMovieTransaction> = mutableListOf()
    private val transactionAdapter by lazy {
        TransactionAdapter {
            movieTransactionItem
        }
    }

    override fun initView() {
        binding.toolbarTransaction.title = "Transaction History"
        binding.rvTransactionItem.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
            val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
            addItemDecoration(SpaceItemDecoration(spaceInPixels))
        }
    }

    override fun initListener() {}

    override fun observeData() {
        getDataTransaction()
    }

    private fun getDataTransaction(){
        val user = viewModel.getCurrentUser()
        movieReference.child(user?.userId ?: "").orderByChild("transactionTime")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val transactionList = mutableListOf<DataMovieTransaction>()

                    for (dataSnapshot in snapshot.children) {
                        val userId = dataSnapshot.child("userId").getValue(String::class.java)
                        val userName = dataSnapshot.child("userName").getValue(String::class.java)
                        val transactionTime =
                            dataSnapshot.child("transactionTime").getValue(String::class.java)

                        val itemNames = dataSnapshot.child("itemName").children.map {
                            it.getValue(String::class.java) ?: ""
                        }
                        val itemPrices = dataSnapshot.child("itemPrice").children.map {
                            it.getValue(Int::class.java) ?: 0
                        }
                        val movieIds = dataSnapshot.child("movieId").children.map {
                            it.getValue(Int::class.java) ?: 0
                        }
                        val totalPrice =
                            dataSnapshot.child("totalPrice").getValue(Int::class.java) ?: 0

                        for (i in itemNames.indices) {
                            val dataTransaction = DataMovieTransaction(
                                userId = userId ?: "",
                                userName = userName ?: "",
                                transactionTime = transactionTime ?: "",
                                movieId = listOf(movieIds[i]),
                                itemName = listOf(itemNames[i]),
                                itemPrice = listOf(itemPrices[i]),
                                totalPrice
                            )
                            transactionList.add(dataTransaction)
                        }
                    }
                    transactionAdapter.submitList(transactionList)
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

}
