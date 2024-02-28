package com.movieappfinal.adapter

import android.view.View
import com.example.core.base.BaseListAdapter
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.databinding.TokenListItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TokenPaymentAdapter(
    private val action: (DataTokenPaymentItem) -> Unit,
) : BaseListAdapter<DataTokenPaymentItem, TokenListItemBinding>(
    TokenListItemBinding::inflate
) {
    private var selectedItem: MutableStateFlow<DataTokenPaymentItem?>? = null

    init {
        selectedItem?.onEach {
            notifyDataSetChanged()
        }?.launchIn(GlobalScope)
    }
    override fun onItemBind(): (DataTokenPaymentItem, TokenListItemBinding, View, Int) -> Unit =
        { item, binding, itemView, position ->
            binding.run {
                tvToken.text = item.token.toString()
                root.isSelected = item == selectedItem?.value

                ivBadge.visibility = if (item == selectedItem?.value) View.VISIBLE else View.GONE
            }
            itemView.setOnClickListener {
                action.invoke(item)
                selectedItem?.value = item
            }
        }
}