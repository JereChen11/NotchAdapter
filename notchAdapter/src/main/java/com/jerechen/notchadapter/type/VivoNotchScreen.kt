package com.jerechen.notchadapter.type

import android.app.Activity
import com.jerechen.notchadapter.INotchScreen


class VivoNotchScreen : INotchScreen {

    override fun isContainNotch(activity: Activity): Boolean {
        var isContainNotch = false
        val containNotchFlog = 0x00000020
        val cls = Class.forName("android.util.FtFeature")
        val get = cls.getMethod("isFtFeatureSupport", Int::class.javaPrimitiveType)
        isContainNotch = get.invoke(cls.newInstance(), containNotchFlog) as Boolean
        return isContainNotch
    }

    override fun getNotchInfo(activity: Activity, notchInfoCallback: INotchScreen.NotchInfoCallback) {
    }

}