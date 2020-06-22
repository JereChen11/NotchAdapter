package com.jerechen.notchadapter.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Rect

/**
 * @author jere
 */
object ScreenUtil {

    /**
     * 检测手机是否处于竖屏状态
     * @param context
     * @return true -> 竖屏
     */
     fun isPortrait(context: Context): Boolean {
        return context.resources
            .configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * 通过刘海的长度与高度计算出左上角与右下角的坐标
     */
    fun calculateNotchRect(context: Context, notchWidth: Int, notchHeight: Int): Rect {
        //获取屏幕的长度与高度
        val screenWidth: Int = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight: Int = Resources.getSystem().displayMetrics.heightPixels
        val left: Int
        val top: Int
        val right: Int
        val bottom: Int
        //竖屏
        if (isPortrait(context)) {
            left = (screenWidth - notchWidth) / 2
            top = 0
            right = left + notchWidth
            bottom = notchHeight
        } else {
            left = 0
            top = (screenHeight - notchHeight) / 2
            right = notchWidth
            bottom = top + notchHeight
        }
        return Rect(left, top, right, bottom)
    }
}