package cn.swordscat.arch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class ArchActivity<V : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)
        beforeSetContentView()
        setContentView(binding.root)
        initData()
        initView()
        initBinding()
        initViewData()
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
     * 双向数据绑定
     */
    open fun initBinding() {}

    /**
     * 填充视图数据
     */
    open fun initViewData() {}

    /**
     * 布局ID
     */
    abstract fun getLayoutId(): Int
}