package com.jerechen.notchadapter

import android.content.Context
import android.os.Build
import com.jerechen.notchadapter.type.*

object NotchManager {

    private val HUAWEI = "huawei"
    private val VIVO = "vivo"
    private val XIAOMI = "xiaomi"
    private val OPPO = "oppo"

    fun getNotchScreen(): INotchScreen? {
        var notchScreen: INotchScreen? = null
        //Android 9及以上，官方才出刘海屏API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            notchScreen = AndroidPNotchScreen()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //判断手机生产厂商
            when(Build.MANUFACTURER.toLowerCase()) {
                HUAWEI -> {
                    notchScreen = HuaWeiNotchScreen()
                }
                VIVO -> {
                    notchScreen = VivoNotchScreen()
                }
                XIAOMI -> {
                    notchScreen = XiaoMiNotchScreen()
                }
                OPPO -> {
                    notchScreen = OppoNotchScreen()
                }
            }
        }
        return notchScreen
    }

    /**
     * 获取状态栏的高度
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}