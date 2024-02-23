package com.movieappfinal.presentation.dashboard

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentHomeBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, AuthViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}