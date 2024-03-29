package com.movieappfinal.core.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.core.module.Module

interface BaseModules {
    fun getModules() : List<Module>
}

abstract class BaseFragment<B: ViewBinding, VM: ViewModel>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
): Fragment() {
    protected lateinit var  binding: B
    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingFactory(layoutInflater, container, false)
        return  binding.root
    }

    abstract fun initView()

    abstract fun initListener()

    abstract fun observeData()
}