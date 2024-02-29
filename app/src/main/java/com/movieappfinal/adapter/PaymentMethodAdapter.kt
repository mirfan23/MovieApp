package com.movieappfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.DataPaymentMethod
import com.movieappfinal.core.domain.model.DataPaymentMethodItem
import com.movieappfinal.databinding.PaymentListBinding

class PaymentMethodAdapter(
    private val list: List<DataPaymentMethod>,
    private val listener: (DataPaymentMethodItem) -> Unit
) : RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {

    inner class PaymentMethodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: PaymentListBinding = PaymentListBinding.bind(itemView)

        fun bind(data: DataPaymentMethod) {
            binding.apply {
                tvTitle.text = data.title
                rvItemPayment.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = PaymentMethodItemAdapter(data.item, listener)
                    setHasFixedSize(true)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentMethodAdapter.PaymentMethodViewHolder {
        val binding = PaymentListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentMethodViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: PaymentMethodAdapter.PaymentMethodViewHolder,
        position: Int
    ) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}