package com.movieappfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.movieappfinal.core.domain.model.DataPaymentMethodItem
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataTokenPaymentItem
import com.movieappfinal.databinding.PaymentListItemBinding
import com.movieappfinal.utils.visibleIf
class PaymentMethodItemAdapter(
    private val list: List<DataPaymentMethodItem>,
    private val listener: (DataPaymentMethodItem) -> Unit
) : RecyclerView.Adapter<PaymentMethodItemAdapter.PaymentMethodItemViewHolder>() {

    private var selectedItem: DataPaymentMethodItem? = null

    inner class PaymentMethodItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: PaymentListItemBinding = PaymentListItemBinding.bind(itemView)

        fun bind(data: DataPaymentMethodItem) {
            binding.apply {
                val colorId = if (data.status) {
                    com.google.android.material.R.color.mtrl_btn_transparent_bg_color
                } else {
                    R.color.bg_grey
                }
                val bg = ResourcesCompat.getColor(root.context.resources, colorId, null)
                itemView.apply {
                    isClickable = data.status
                    isEnabled = data.status
                    setBackgroundColor(bg)
                }
                if (data == selectedItem) {
                    itemView.setBackgroundColor(itemView.resources.getColor(R.color.bg_grey))
                } else {
                    itemView.setBackgroundColor(ResourcesCompat.getColor(root.context.resources, colorId, null))
                }
                ivIcon.apply {
                    visibleIf(
                        data.label.equals("OVO", true).not() && data.label.equals(
                            "GoPay",
                            true
                        ).not()
                    )
                    load(data.image)
                }
                sivIcon.apply {
                    visibleIf(data.label.equals("OVO", true) || data.label.equals("GoPay", true))
                    load(data.image)
                }
                tvName.text = data.label
                itemView.setOnClickListener {
                    listener.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentMethodItemAdapter.PaymentMethodItemViewHolder {
        val binding = PaymentListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentMethodItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PaymentMethodItemAdapter.PaymentMethodItemViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
