package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.core.domain.model.DataCart
import com.movieappfinal.databinding.CartListCardBinding

class CartAdapter(
    private val action: (DataCart) -> Unit,
    private val remove: (DataCart) -> Unit,
    private val checkbox: (Int, Boolean) -> Unit
) :
    BaseListAdapter<DataCart, CartListCardBinding>(CartListCardBinding::inflate) {
    override fun onItemBind(): (DataCart, CartListCardBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                imgThumbnailCart.load(item.image)
                tvItemName.text = item.movieTitle
                tvPrice.text = item.moviePrice.toString()
                btnDeleteCart.setOnClickListener {
                    remove.invoke(item)
                }
                cbCart.setOnCheckedChangeListener { _, isChecked ->
                    checkbox(item.cartId, isChecked)
                }
            }
            itemView.setOnClickListener {
                action.invoke(item)
            }
        }

}