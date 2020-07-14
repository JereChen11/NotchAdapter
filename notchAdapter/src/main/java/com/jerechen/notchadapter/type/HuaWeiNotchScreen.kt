package com.jerechen.notchadapter.type

import android.app.Activity
import com.jerechen.notchadapter.INotchScreen
import com.jerechen.notchadapter.utils.ScreenUtil

/**
 * @author jere
 */
class HuaWeiNotchScreen: INotchScreen {
    override fun isContainNotch(activity: Activity): Boolean {
        var isContainNotch = false
        val cl = activity.classLoader
        val hwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
        val get = hwNotchSizeUtil.getMethod("hasNotchInScreen")
        isContainNotch = get.invoke(hwNotchSizeUtil) as Boolean
        return isContainNotch
    }

    override fun getNotchInfo(activity: Activity, notchInfoCallback: INotchScreen.NotchInfoCallback) {
        val cl = activity.classLoader
        val hwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
        val get = hwNotchSizeUtil.getMethod("getNotchSize")
        val notchSizeArray = get.invoke(hwNotchSizeUtil) as IntArray
        //没有刘海屏
        if (notchSizeArray.size < 2) {
            return
        }
        val notchWidth = notchSizeArray[0]
        val notchHeight = notchSizeArray[1]

        val notchRect = ScreenUtil.calculateNotchRect(activity, notchWidth, notchHeight)
        notchInfoCallback.getNotchRect(notchRect)
    }
}