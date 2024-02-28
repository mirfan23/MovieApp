package com.movieappfinal.presentation.others

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentSettingBinding
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment<FragmentSettingBinding, HomeViewModel>(FragmentSettingBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {
    }

    override fun observeData() {}

}