package com.movieappfinal.presentation.dashboard

import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.google.firebase.auth.FirebaseAuth
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.databinding.FragmentDashboardBinding
import com.movieappfinal.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, DashboardViewModel>(FragmentDashboardBinding::inflate) {
    override val viewModel: DashboardViewModel by viewModel()
    private lateinit var navController: NavController
    private var auth: FirebaseAuth? = null


    override fun initView() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_dashboard) as NavHostFragment
        navController = navHostFragment.navController

        val userName = viewModel.getCurrentUser()
        binding.toolbarDashboard.title = userName.let { it?.userName }
        userName?.let { viewModel.putUID(it.userId) }
        userName?.let { viewModel.saveProfileName(it.userName) }
    }

    override fun initListener() = with(binding) {
        toolbarDashboard.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cart_menu -> {
                    findNavController().navigate(R.id.action_dashboardFragment_to_cartFragment)
                    true
                }

                R.id.my_token_menu -> {
                    findNavController().navigate(R.id.action_dashboardFragment_to_myTokenFragment)
                    true
                }

                else -> false
            }
        }
        toolbarDashboard.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val metrics = activity?.let { activity ->
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(
                activity
            )
        }

        val withDP = metrics?.bounds?.width()?.div(resources.displayMetrics.density)

        if(withDP != null) {
            when {
                withDP < 600f -> {
                    val bottomNav = binding.navView as NavigationView
                    bottomNav.setupWithNavController(navController)

                    bottomNav.setNavigationItemSelectedListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.homeFragment -> {
                                navController.navigate(R.id.homeFragment)
                                binding.drawerLayout.closeDrawer(GravityCompat.START)
                                true
                            }

                            R.id.searchFragment -> {
                                navController.navigate(R.id.searchFragment)
                                binding.drawerLayout.closeDrawer(GravityCompat.START)
                                true
                            }

                            R.id.wishlistFragment -> {
                                navController.navigate(R.id.wishlistFragment)
                                binding.drawerLayout.closeDrawer(GravityCompat.START)
                                true
                            }

                            R.id.transactionFragment -> {
                                navController.navigate(R.id.transactionFragment)
                                binding.drawerLayout.closeDrawer(GravityCompat.START)
                                true
                            }

                            R.id.settingFragment -> {
                                navController.navigate(R.id.settingFragment)
                                binding.drawerLayout.closeDrawer(GravityCompat.START)
                                true
                            }
                            R.id.logout_item -> {
                                auth?.signOut()
                                viewModel.clearAllSession()
                                activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                                    ?.findNavController()
                                    ?.navigate(R.id.action_dashboardFragment_to_loginFragment)
                                true
                            }
                            else -> false
                        }
                    }
                }

                withDP < 840f -> {
                    val navigationRailView = binding.navView as NavigationRailView
                    navigationRailView.setOnItemSelectedListener { item ->
                        when (item.itemId) {
                            R.id.homeFragment -> {
                                navController.navigate(R.id.homeFragment)
                                true
                            }
                            R.id.searchFragment -> {
                                navController.navigate(R.id.searchFragment)
                                true
                            }
                            R.id.wishlistFragment -> {
                                navController.navigate(R.id.wishlistFragment)
                                true
                            }
                            R.id.transactionFragment -> {
                                navController.navigate(R.id.transactionFragment)
                                true
                            }
                            R.id.settingFragment -> {
                                navController.navigate(R.id.settingFragment)
                                true
                            }
                            R.id.logout_item -> {
                                auth?.signOut()
                                viewModel.clearAllSession()
                                activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                                    ?.findNavController()
                                    ?.navigate(R.id.action_dashboardFragment_to_loginFragment)
                                true
                            }
                            else -> false
                        }
                    }
                }

                else -> {
                    val bottomNav = binding.navView as NavigationView
                    bottomNav.setupWithNavController(navController)
                }
            }
        }
        binding.navView.setOnClickListener { menuItem ->
            when (menuItem.id) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.wishlistFragment -> {
                    navController.navigate(R.id.wishlistFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.transactionFragment -> {
                    navController.navigate(R.id.transactionFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.settingFragment -> {
                    navController.navigate(R.id.settingFragment)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.logout_item -> {
                    auth?.signOut()
                    viewModel.clearAllSession()
                    activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                        ?.findNavController()
                        ?.navigate(R.id.action_dashboardFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }

    }

    override fun observeData() {}
}
