package com.movieappfinal.presentation.others

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentTopUpBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopUpFragment : BaseFragment<FragmentTopUpBinding, AuthViewModel>(FragmentTopUpBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}