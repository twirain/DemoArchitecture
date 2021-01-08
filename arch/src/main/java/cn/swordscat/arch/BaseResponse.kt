package cn.swordscat.arch

interface BaseResponse {
    /**
     * 业务状态判断
     */
    fun isOk(): Boolean

    /**
     * 错误信息
     */
    fun getErrorMsg(): String?
}