package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.core.domain.model.DataCheckout
import com.movieappfinal.databinding.MoviePaymentItemBinding

class CheckoutAdapter(
    private val action: (DataCheckout) -> Unit,
) :
    BaseListAdapter<DataCheckout, MoviePaymentItemBinding>(MoviePaymentItemBinding::inflate) {
    override fun onItemBind(): (DataCheckout, MoviePaymentItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                imgThumbnailCart.load(item.image)
                tvItemName.text = item.itemName
                tvPrice.text = item.itemPrice.toString()
            }
            itemView.setOnClickListener {
                action.invoke(item)
            }
        }
}