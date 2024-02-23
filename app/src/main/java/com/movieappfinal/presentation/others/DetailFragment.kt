package com.movieappfinal.presentation.others

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentDetailBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, AuthViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}