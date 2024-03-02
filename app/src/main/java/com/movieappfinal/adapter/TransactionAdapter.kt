package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.core.domain.model.DataMovieTransaction
import com.movieappfinal.databinding.CartListCardBinding
import com.movieappfinal.databinding.TransactionItemBinding

class TransactionAdapter(
    private val action: (DataMovieTransaction) -> Unit,
) :
    BaseListAdapter<DataMovieTransaction, TransactionItemBinding>(TransactionItemBinding::inflate) {
    override fun onItemBind(): (DataMovieTransaction, TransactionItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                tvItemName.text = item.itemName.toString()
                tvPrice.text = item.itemPrice.toString()
                tvDateTime.text = item.transactionTime
            }
            itemView.setOnClickListener {
                action.invoke(item)
            }
        }
}