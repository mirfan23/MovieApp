package com.movieappfinal.presentation.dashboard

import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentWishlistBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishlistFragment : BaseFragment<FragmentWishlistBinding, AuthViewModel>(FragmentWishlistBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {

    }

    override fun initListener() {}

    override fun observeData() {}

}