package com.jerechen.notchadapter.type

import android.app.Activity
import android.util.Log
import com.jerechen.notchadapter.INotchScreen


class VivoNotchScreen : INotchScreen {

    override fun isContainNotch(activity: Activity): Boolean {
        var isContainNotch = false
        try {
            val containNotchFlog = 0x00000020
            val cls = Class.forName("android.util.FtFeature")
            val get = cls.getMethod("isFtFeatureSupport", Int::class.javaPrimitiveType)
            isContainNotch = get.invoke(cls.newInstance(), containNotchFlog) as Boolean
        } catch (e: Exception) {
            Log.e("VivoNotchScreen", "isContainNotch ${e.message}")
        }
        return isContainNotch
    }

    override fun getNotchInfo(activity: Activity, notchInfoCallback: INotchScreen.NotchInfoCallback) {
    }

}