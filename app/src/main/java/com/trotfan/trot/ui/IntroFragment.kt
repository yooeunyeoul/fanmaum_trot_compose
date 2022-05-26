package com.trotfan.trot.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.trotfan.trot.base.BaseFragment
import com.trotfan.trot.databinding.FragmentIntroBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment @Inject constructor() : BaseFragment<FragmentIntroBinding>() {
    override fun bindingBefore() {
    }

    override fun initViewBinding() {
    }

    override fun bindingAfter() {
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIntroBinding = FragmentIntroBinding.inflate(layoutInflater, container, false)
}