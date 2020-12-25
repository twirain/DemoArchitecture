package cn.swordscat.arch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)
        beforeSetContentView()
        setContentView(binding.root)
        initData()
        initView()
        observerLiveData()
    }

    /**
     * 在 [setContentView] 之前会被调用
     */
    open fun beforeSetContentView() {}

    /**
     * 初始化数据相关
     */
    open fun initData() {}

    /**
     * 初始化视图相关
     */
    open fun initView() {}

    /**
     * 观察viewModel的LiveData
     */
    open fun observerLiveData() {}

    /**
     * 布局ID
     */
    abstract fun getLayoutId(): Int
}