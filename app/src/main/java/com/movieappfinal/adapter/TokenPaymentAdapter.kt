package com.movieappfinal.adapter

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.example.core.base.BaseListAdapter
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.databinding.TokenListItemBinding
import com.movieappfinal.utils.currency
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TokenPaymentAdapter(
    private val action: (DataTokenPaymentItem) -> Unit,
) : BaseListAdapter<DataTokenPaymentItem, TokenListItemBinding>(
    TokenListItemBinding::inflate
) {
    private var selectedItem: DataTokenPaymentItem? = null
    override fun onItemBind(): (DataTokenPaymentItem, TokenListItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                tvToken.text = item.token.toString()
                tvCost.text = currency(item.price)
                if (item == selectedItem) {
                    cardToken.setBackgroundColor(itemView.resources.getColor(R.color.bg_grey))
                } else {
                    cardToken.setBackgroundColor(cardItem.cardBackgroundColor.defaultColor)
                }
                itemView.setOnClickListener {
                    action.invoke(item)
                    selectedItem = item
                    notifyDataSetChanged()
                }
            }
        }

    fun clearSelectedItem() {
        selectedItem = null
        notifyDataSetChanged()
    }
}
