package com.movieappfinal.presentation.others

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.movieappfinal.R
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentSettingBinding
import com.movieappfinal.utils.Constant.LANGUAGE_EN
import com.movieappfinal.utils.Constant.LANGUAGE_IN
import com.movieappfinal.utils.CustomSnackbar
import com.movieappfinal.utils.checkIf
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment :
    BaseFragment<FragmentSettingBinding, HomeViewModel>(FragmentSettingBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()

    override fun initView() {
        viewModel.getThemeStatus()
        binding.tvLight.text = resources.getString(R.string.light)
        binding.tvDark.text = resources.getString(R.string.dark)
        binding.tvID.text = resources.getString(R.string.id)
        binding.tvEN.text = resources.getString(R.string.en)
        binding.tvLanguageSetting.text = getString(R.string.language_setting)
        binding.tvThemeSetting.text = getString(R.string.theme_text)
        binding.btnDeleteAccount.text = getString(R.string.delete_account_setting)

        val userName = viewModel.getCurrentUser()
        binding.tvUsername.text = userName.let { it?.userName }

        val isLanguageChecked = viewModel.getLanguageStatus()
        if (isLanguageChecked) {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LANGUAGE_IN)
            AppCompatDelegate.setApplicationLocales(appLocale)
            binding.switchLanguage.isChecked = true
        } else {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LANGUAGE_EN)
            AppCompatDelegate.setApplicationLocales(appLocale)
            binding.switchLanguage.isChecked = false
        }
    }

    override fun initListener() {
        binding.switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LANGUAGE_IN)
                AppCompatDelegate.setApplicationLocales(appLocale)
            } else {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LANGUAGE_EN)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
            val lang = resources.configuration.locales[0].language
            viewModel.putLanguageStatus(lang)
        }
        binding.switchTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    context?.let { viewModel.putThemeStatus(true) }
                }

                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    context?.let { viewModel.putThemeStatus(false) }
                }
            }
        }
        binding.btnDeleteAccount.setOnClickListener {
            viewModel.deleteAccount().launchAndCollectIn(viewLifecycleOwner) {
                if (it) {
                    activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container)
                        ?.findNavController()
                        ?.navigate(R.id.action_dashboardFragment_to_loginFragment)
                } else {
                    context?.let { ctx ->
                        CustomSnackbar.showSnackBar(
                            ctx,
                            binding.root,
                            getString(R.string.delete_account_failed)
                        )
                    }
                }
            }
        }
    }

    override fun observeData() {
        viewModel.run {
            theme.launchAndCollectIn(viewLifecycleOwner) {
                binding.switchTheme.checkIf(it)
            }
        }

    }

}
