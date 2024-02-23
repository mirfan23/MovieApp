package com.movieappfinal.presentation.others

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentTokenPaymentBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TokenPaymentFragment : BaseFragment<FragmentTokenPaymentBinding, AuthViewModel>(FragmentTokenPaymentBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}