package cn.swordscat.demoarchitecture

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import cn.swordscat.arch.BaseActivity
import cn.swordscat.arch.LOADED
import cn.swordscat.arch.LOADING
import cn.swordscat.demoarchitecture.databinding.ActivityMainBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var loadingPopup: LoadingPopupView

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        loadingPopup = XPopup.Builder(this).asLoading()
    }

    override fun initBinding() {
        viewModel.defaultLoadingHandler.observe(this, Observer {
            // 建议对loading的组件进行最短时间限制，避免加载中到完成的时间间隔太快，出现”闪烁“
            when (it.state) {
                LOADING -> {
                    loadingPopup.setTitle(it.tips)
                    loadingPopup.show()
                }
                LOADED -> {
                    loadingPopup.dismiss()
                }
            }
        })
    }

    override fun initViewData() {
        binding.viewModel = viewModel
        binding.clickListener = clickListener
    }

    private val clickListener = View.OnClickListener {
        when (it.id) {
            binding.btnMockWork.id -> {
                viewModel.mockWork()
            }
        }
    }
}