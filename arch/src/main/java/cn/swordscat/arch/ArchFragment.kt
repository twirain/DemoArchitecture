package cn.swordscat.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class ArchFragment<V: ViewDataBinding> : Fragment() {
    private lateinit var binding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        initBinding()
        initViewData()
    }

    /**
     * 初始化数据相关
     */
    open fun initData() {}

    /**
     * 初始化视图相关
     */
    open fun initView() {}

    /**
     * 数据绑定
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