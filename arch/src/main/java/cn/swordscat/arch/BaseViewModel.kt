package cn.swordscat.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    // 请求加载状态
    val defaultLoadingHandler = MutableLiveData<LoadResult>()

    /**
     * 如果 [loadingTips] 为空，则不会对 [defaultLoadingHandler] 进行更改
     *
     * @param block 业务请求
     * @param result 请求结果
     * @param loadingTips 加载显示的tips
     */
    fun <T> resultNetScope(
        block: suspend () -> T,
        result: (result: Result<T>) -> Unit,
        loadingTips: String? = DEFAULT_TIPS
    ) {
        loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADING, loadingTips) }
        viewModelScope.launch {
            val tmp = kotlin.runCatching {
                // 可在此基础上增加基础业务状态判断
                block()
            }
            loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADED, null) }
            result(tmp)
        }
    }

    /**
     * 如果 [loadingTips] 为空，则不会对 [defaultLoadingHandler] 进行更改
     *
     * @param block 业务请求
     * @param success 请求成功的动作
     * @param fail 请求异常的动作
     */
    fun <T> netScope(
        block: suspend () -> T,
        success: suspend (result: T) -> Unit,
        fail: suspend (exception: Throwable) -> Unit = {
            // 执行默认动作。比如显示Toast
        },
        loadingTips: String? = DEFAULT_TIPS
    ) {
        loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADING, loadingTips) }
        viewModelScope.launch {
            try {
                // 可在此基础上增加基础业务状态判断
                loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADED, null) }
                success(block())
            } catch (e: Exception) {
                loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADED, null) }
                fail(e)
            }
        }
    }
}