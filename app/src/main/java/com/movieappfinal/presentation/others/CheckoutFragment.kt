package com.movieappfinal.presentation.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentCheckoutBinding
import com.movieappfinal.databinding.FragmentSearchBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment : BaseFragment<FragmentCheckoutBinding, AuthViewModel>(FragmentCheckoutBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()

    override fun initView() {}

    override fun initListener() {}

    override fun observeData() {}

}