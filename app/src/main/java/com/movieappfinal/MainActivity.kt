package com.movieappfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.ActivityMainBinding
import com.movieappfinal.utils.Constant.LANGUAGE_EN
import com.movieappfinal.utils.Constant.LANGUAGE_IN
import com.movieappfinal.viewmodel.DashboardViewModel
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeStatus()
        viewModel.theme.launchAndCollectIn(this){
            AppCompatDelegate.setDefaultNightMode(if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }

        val isLanguageChecked = viewModel.getLanguageStatus()
        if (isLanguageChecked){
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LANGUAGE_IN)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }else{
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LANGUAGE_EN)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }

    }
}