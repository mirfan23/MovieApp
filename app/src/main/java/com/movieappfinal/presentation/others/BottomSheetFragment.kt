package com.movieappfinal.presentation.others

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.DataPaymentMethod
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
    private var filterListener: OnFilterFragmentListener? = null
    private val listPaymentMethod: MutableList<DataPaymentMethod> = mutableListOf()
    private lateinit var paymentMethodAdapter: PaymentMethodAdapter

    interface OnFilterFragmentListener {
        fun onFilterApplied(
            sort: String?, brand: String?
        )
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

    private fun initView() { binding.apply {
        paymentMethodAdapter = PaymentMethodAdapter(listPaymentMethod)
        rvItemPayment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = paymentMethodAdapter
        }
    } }

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
                println("MASUK: ${getDataPayment()}")
            }
            getDataPayment()

        }
    }

    fun setFilterListener(listener: OnFilterFragmentListener) {
        filterListener = listener
    }

    private fun getDataPayment() {
        viewModel.doPaymentMethod().launchAndCollectIn(viewLifecycleOwner) {
            println("MASUK FRAGMENT : $it")
            listPaymentMethod.addAll(it)
            paymentMethodAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheetDialog"
    }
}
