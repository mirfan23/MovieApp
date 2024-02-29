package com.movieappfinal.presentation.others

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.DataPaymentMethod
import com.movieappfinal.core.domain.model.DataPaymentMethodItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.movieappfinal.adapter.PaymentMethodAdapter
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentBottomSheetBinding
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private val viewModel: DashboardViewModel by viewModel()
    private val listPaymentMethod: MutableList<DataPaymentMethod> = mutableListOf()
    private lateinit var paymentMethodAdapter: PaymentMethodAdapter
    var listener: BottomSheetListener? = null
    private val bottomListener: (DataPaymentMethodItem) -> Unit = {
        listener?.onItemSelected(it)
        dismiss()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observeData()
    }

    private fun initView() {
        binding.apply {
            paymentMethodAdapter = PaymentMethodAdapter(
                listPaymentMethod,
                bottomListener
            )
            rvItemPayment.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = paymentMethodAdapter
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    private fun initListener() {}

    private fun observeData() {
        with(viewModel) {
            getConfigStatusUpdatePayment().launchAndCollectIn(viewLifecycleOwner) {
                getDataPayment()
            }
            getDataPayment()

        }
    }

    private fun getDataPayment() {
        viewModel.doPaymentMethod().launchAndCollectIn(viewLifecycleOwner) {
            listPaymentMethod.clear()
            listPaymentMethod.addAll(it)
            paymentMethodAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheetDialog"
        const val ARG_SELECTED_PAYMENT = "selected_payment"

        fun newInstance(selectedPayment: DataPaymentMethod): BottomSheetFragment {
            val args = Bundle().apply {
                putParcelable(ARG_SELECTED_PAYMENT, selectedPayment)
            }
            return BottomSheetFragment().apply {
                arguments = args
            }
        }
    }

    interface BottomSheetListener {
        fun onItemSelected(item: DataPaymentMethodItem)
    }
}