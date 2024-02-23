package com.movieappfinal.presentation.authentication

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentProfileBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding, AuthViewModel>(FragmentProfileBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}