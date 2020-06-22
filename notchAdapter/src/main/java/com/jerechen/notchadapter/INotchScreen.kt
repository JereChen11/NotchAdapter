package com.jerechen.notchadapter

import android.app.Activity
import android.graphics.Rect

interface INotchScreen {

    /**
     * 当下屏幕是否存在刘海？
     */
    fun isContainNotch(activity: Activity): Boolean

    /**
     * 获取刘海信息参数
     */
    fun getNotchInfo(activity: Activity, notchInfoCallback: NotchInfoCallback)

    interface NotchInfoCallback {
        fun getNotchRect(notchRectInfo: Rect)
    }
}