package com.jerechen.notchadapter.type

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Window
import com.jerechen.notchadapter.INotchScreen
import com.jerechen.notchadapter.utils.ScreenUtil
import java.lang.reflect.Method

/**
 * @author jere
 */
class XiaoMiNotchScreen : INotchScreen {

    //参考文档： https://dev.mi.com/console/doc/detail?pId=1293

    override fun isContainNotch(activity: Activity): Boolean {
        try {
            val getInt = Class.forName("android.os.SystemProperties").getMethod(
                "getInt",
                String::class.java,
                Int::class.javaPrimitiveType
            )
            //值为1时则是 Notch 屏手机
            val notchStatusId = getInt.invoke(null, "ro.miui.notch", 0) as Int
            Log.e("jereTest", "isContainNotch = $notchStatusId")
            return notchStatusId == 1
        } catch (e: Exception) {
            Log.e("XiaoMiNotchScreen", "isContainNotch ${e.message}")
        }
        return false
    }

    override fun getNotchInfo(activity: Activity, notchInfoCallback: INotchScreen.NotchInfoCallback) {
        try {
            val notchRect = ScreenUtil.calculateNotchRect(activity, getNotchWidth(activity), getNotchHeight(activity))
            notchInfoCallback.getNotchRect(notchRect)
        } catch (e: Exception) {
            Log.e("XiaoMiNotchScreen", "getNotchInfo ${e.message}")
        }
    }

    /**
     * 获取刘海区域的高度
     */
    private fun getNotchHeight(context:Context): Int {
        var notchHeight = 0
        val resourceId: Int = context.resources.getIdentifier("notch_height", "dimen", "android")
        if (resourceId > 0) {
            notchHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        Log.e("jereTest", "notch_height = $notchHeight")
        return notchHeight
    }

    /**
     *  获取刘海区域的长度
     */
    private fun getNotchWidth(context: Context): Int {
        var notchWidth = 0
        val resourceId: Int = context.resources.getIdentifier("notch_width", "dimen", "android")
        if (resourceId > 0) {
            notchWidth = context.resources.getDimensionPixelSize(resourceId)
        }
        Log.e("jereTest", "notch_width = $notchWidth")
        return notchWidth
    }

    /**
     * 对特定 Window 作处理
     *
     * 0x00000100 | 0x00000200 | 0x00000400 横竖屏都绘制到耳朵区
     */
    fun addExtraFlags(activity: Activity) {
        val flag = 0x00000100 or 0x00000200 or 0x00000400
        val method: Method = Window::class.java.getMethod(
            "addExtraFlags",
            Int::class.javaPrimitiveType
        )
        method.invoke(activity.window, flag)
    }
}