package cn.swordscat.demoarchitecture

import androidx.lifecycle.MutableLiveData
import cn.swordscat.arch.ArchViewModel
import kotlinx.coroutines.delay

class MainViewModel : ArchViewModel() {
    val greeting = MutableLiveData("Hello World!")
    private var loadNum = 1

    fun mockWork() {
        resultNetScope({
            delay(1000)
            // 模拟多个loading状态触发
            resultNetScope({
                delay(2000)
            }, {
                // 如果不关心失败状态，直接处理结果
                it.getOrNull()
                // 或者直接处理异常
                it.exceptionOrNull()
            }, loadingTips = loadNum++.toString())
            delay(2000)
        }, {
            when {
                it.isSuccess -> {
                    // 成功
                }
                it.isFailure -> {
                    // 失败
                }
            }
        }, loadingTips = loadNum++.toString())
    }
}