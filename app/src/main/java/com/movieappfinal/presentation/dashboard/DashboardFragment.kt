package com.movieappfinal.presentation.dashboard

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentDashboardBinding
import com.movieappfinal.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, AuthViewModel>(FragmentDashboardBinding::inflate) {
    override val viewModel: AuthViewModel by viewModel()
    private lateinit var navController: NavController

    override fun initView() {

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_dashboard) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun initListener() = with(binding) {
        toolbarDashboard.setOnMenuItemClickListener { menutItem ->
            when (menutItem.itemId) {
                R.id.cart_menu -> {
                    findNavController().navigate(R.id.action_dashboardFragment_to_cartFragment)
                    true
                }

                R.id.my_token_menu -> {
                    findNavController().navigate(R.id.action_dashboardFragment_to_myTokenFragment)
                    true
                }

                R.id.setting_menu -> {
                    findNavController().navigate(R.id.action_dashboardFragment_to_settingFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun observeData() {}

}