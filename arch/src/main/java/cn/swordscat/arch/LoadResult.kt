package cn.swordscat.arch

import androidx.annotation.IntDef

/**
 * 加载状态模型
 */
class LoadResult(val state: @LoadState Int, val tips: String? = DEFAULT_TIPS)

const val DEFAULT_TIPS = "正在加载中..."
const val LOADING = 0
const val LOADED = 1

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.TYPE)
@IntDef(
    LOADING,
    LOADED
)
annotation class LoadState