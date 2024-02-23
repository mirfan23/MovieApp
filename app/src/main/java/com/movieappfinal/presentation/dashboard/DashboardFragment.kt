package com.movieappfinal.presentation.dashboard

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentDashboardBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardFragment : BaseFragment<FragmentDashboardBinding, AuthViewModel>(FragmentDashboardBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}