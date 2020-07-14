package com.jerechen.notchadapter.type

import android.app.Activity
import android.graphics.Rect
import android.text.TextUtils
import android.util.Log
import com.jerechen.notchadapter.INotchScreen
import com.jerechen.notchadapter.utils.ScreenUtil

/**
 * Oppo直接返回刘海屏的坐标
 *
 * @author jere
 */
class OppoNotchScreen : INotchScreen {

    override fun isContainNotch(activity: Activity): Boolean {
        try {
            return activity.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
        } catch (e: Exception) {
            Log.e("OppoNotchScreen", "isContainNotch ${e.message}")
        }
        return false
    }

    override fun getNotchInfo(
        activity: Activity,
        notchInfoCallback: INotchScreen.NotchInfoCallback
    ) {
        try {
            //没有刘海屏
            if (!isContainNotch(activity)) {
                return
            }
            val notchInfo = getNotchInfo()
            //没有刘海屏
            if (TextUtils.isEmpty(notchInfo)) {
                return
            }
            val left: Int
            val top: Int
            val right: Int
            val bottom: Int
            Log.e("jereTest", "Oppo notchInfo = $notchInfo")
            val notchSplit: List<String> = notchInfo.split(":")
            //左上角坐标
            val leftTopPoint = notchSplit[0]
            //右下角坐标
            val rightBottomPoint = notchSplit[1]
            val leftAndTop = leftTopPoint.split(",")
            val rightAndBottom = rightBottomPoint.split(",")

            //竖屏
            if (ScreenUtil.isPortrait(activity)) {
                left = leftAndTop[0].toInt()
                top = leftAndTop[1].toInt()
                right = rightAndBottom[0].toInt()
                bottom = rightAndBottom[1].toInt()
            } else {
                left = leftAndTop[1].toInt()
                top = leftAndTop[0].toInt()
                right = rightAndBottom[1].toInt()
                bottom = rightAndBottom[0].toInt()
            }
            val notchRect = Rect(left, top, right, bottom)
            notchInfoCallback.getNotchRect(notchRect)
        } catch (e: Exception) {
            Log.e("OppoNotchScreen", "getNotchInfo ${e.message}")
        }
    }


    /**
     * 获取刘海区域 左上角与右下角 坐标
     *
     * @return notchInfo 左上角坐标:右下角坐标
     * 例如：OPPO PBATOO 获取到的刘海坐标属性为： 229,0:492,53
     */
    private fun getNotchInfo(): String {
        val notchInfo: String
        val cls = Class.forName("android.os.SystemProperties")
        val get = cls.getMethod("get", String::class.java)
        notchInfo = get.invoke(cls.newInstance(), "ro.oppo.screen.heteromorphism") as String
        return notchInfo
    }

}