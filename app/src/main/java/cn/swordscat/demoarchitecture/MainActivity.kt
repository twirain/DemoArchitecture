package cn.swordscat.demoarchitecture

import androidx.activity.viewModels
import cn.swordscat.arch.BaseActivity
import cn.swordscat.demoarchitecture.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        binding.viewModel = viewModel
    }
}