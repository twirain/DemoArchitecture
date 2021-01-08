package cn.swordscat.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class ArchViewModel : ViewModel() {
    // 请求加载状态
    val defaultLoadingHandler = MutableLiveData<LoadResult>()

    /**
     * 如果 [loadingTips] 为空，则不会对 [defaultLoadingHandler] 进行更改
     *
     * @param block 业务请求
     * @param result 请求结果
     * @param loadingTips 加载显示的tips
     */
    fun <T: BaseResponse> resultNetScope(
        block: suspend () -> T,
        result: (result: Result<T>) -> Unit,
        loadingTips: String? = DEFAULT_TIPS
    ) {
        loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADING, loadingTips) }
        viewModelScope.launch {
            val tmp = kotlin.runCatching {
                val response = block()
                if (!response.isOk()) throw BusinessException(response.getErrorMsg())
                response
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
    fun <T: BaseResponse> netScope(
        block: suspend () -> T,
        success: suspend (result: T) -> Unit,
        fail: suspend (exception: Throwable) -> Unit = {
            // TODO: 2021/1/8 执行一些默认的错误处理
//            LogKits.business(it.message)
//            if (AppConfig.isDebug()) {
//                ToastUtils.showShort("${it.message}")
//            } else {
//                // 正式环境下显示友好提示
//                ToastUtils.showShort(Tips.NET_ERROR)
//            }
        },
        loadingTips: String? = DEFAULT_TIPS
    ) {
        loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADING, loadingTips) }
        viewModelScope.launch {
            try {
                val result = block()
                loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADED, null) }
                if (result.isOk()) success(result) else fail(BusinessException(result.getErrorMsg()))
            } catch (e: Exception) {
                loadingTips?.let { defaultLoadingHandler.value = LoadResult(LOADED, null) }
                fail(e)
            }
        }
    }
}