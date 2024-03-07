package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.core.domain.model.DataListMovie
import com.movieappfinal.core.domain.model.DataMoviePayment
import com.movieappfinal.databinding.PaymentMovieItemBinding

class MoviePaymentItemAdapter(
    private val action: (DataMoviePayment) -> Unit,
) :
    BaseListAdapter<DataMoviePayment, PaymentMovieItemBinding>(PaymentMovieItemBinding::inflate) {
    override fun onItemBind(): (DataMoviePayment, PaymentMovieItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                ivMoviePoster.load(item.image)
                tvMovieTitle.text = item.itemName
            }
            itemView.setOnClickListener {
                action.invoke(item)
            }
        }
}
