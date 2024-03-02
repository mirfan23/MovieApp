package com.movieappfinal.presentation.dashboard

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.movieappfinal.R
import com.movieappfinal.adapter.TransactionAdapter
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentTransactionBinding
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.SpaceItemDecoration
import com.movieappfinal.viewmodel.AuthViewModel
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding, DashboardViewModel>(FragmentTransactionBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private val database = FirebaseDatabase.getInstance().reference
    private val movieReference = database.child("movie_transaction")
    val movieTransactionItem: MutableList<DataMovieTransaction> = mutableListOf()
    private val transactionAdapter by lazy {
        TransactionAdapter {
            movieTransactionItem
        }
    }

    override fun initView() {
        binding.toolbarTransaction.title = "Transaction History"

        var user = viewModel.getCurrentUser()
        movieReference.child(user?.userId ?: "").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val dataTransaction = dataSnapshot.getValue(DataMovieTransaction::class.java)
                    dataTransaction?.let { movieTransactionItem.addAll(listOf(it)) }
                    println("MASUK: $movieTransactionItem")
                }
                transactionAdapter.submitList(movieTransactionItem)
                binding.rvTransactionItem.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = transactionAdapter
                    val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
                    addItemDecoration(SpaceItemDecoration(spaceInPixels))
                }
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

    override fun initListener() {}

    override fun observeData() {}

}