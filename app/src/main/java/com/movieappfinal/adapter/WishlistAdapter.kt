package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataWishlist
import com.movieappfinal.databinding.WishlistCardBinding

class WishlistAdapter(private val action: (DataWishlist) -> Unit, private val remove: (DataWishlist) -> Unit) :
    BaseListAdapter<DataWishlist, WishlistCardBinding>(WishlistCardBinding::inflate) {
    override fun onItemBind(): (DataWishlist, WishlistCardBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                btnAddCart.text = root.context.getString(R.string.add_to_cart_btn)
                ivMoviePoster.load(item.image)
                tvMovieName.text = item.movieTitle
                tvMoviePrice.text = item.moviePrice.toString()
                btnDeleteWishlist.setOnClickListener {
                    remove.invoke(item)
                }
            }
            itemView.setOnClickListener {
                action.invoke(item)
            }
        }
}