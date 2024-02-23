package com.movieappfinal.presentation.dashboard

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentTransactionBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment : BaseFragment<FragmentTransactionBinding, AuthViewModel>(FragmentTransactionBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}