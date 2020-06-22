package com.jerechen.notchadapter.type

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.jerechen.notchadapter.INotchScreen

/**
 * @author jere
 */
@RequiresApi(Build.VERSION_CODES.P)
class AndroidPNotchScreen : INotchScreen {

    override fun isContainNotch(activity: Activity): Boolean {
        var isContainNotch = false
        getNotchRectList(activity, object : GetNotchRectListener {
            override fun onResult(rectList: List<Rect>) {
                isContainNotch = rectList.isNotEmpty()
            }
        })
        return isContainNotch
    }

    override fun getNotchInfo(activity: Activity, notchInfoCallback: INotchScreen.NotchInfoCallback) {
        getNotchRectList(activity, object : GetNotchRectListener {
            override fun onResult(rectList: List<Rect>) {
                if (rectList.isNotEmpty()) {
                    //只支持只有一块刘海屏幕
                    notchInfoCallback.getNotchRect(rectList[0])
                }
            }

        })

    }

    private fun getNotchRectList(activity: Activity, notchRectListener: GetNotchRectListener) {
        //设置刘海区域展示的模式, 会允许应用程序的内容延伸到刘海区域。
        val window = activity.window
        // 延伸显示区域到耳朵区
        val lp = window.attributes
        //在竖屏模式和横屏模式下，内容都会呈现到刘海区域中
        lp.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.attributes = lp
        // 允许内容绘制到耳朵区
        val decorView = window.decorView
        //设置真正的全屏显示
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        decorView.post {
            kotlin.run {
                val windowInsets = decorView.rootWindowInsets
                if (windowInsets != null) {
                    //获取刘海屏的坐标位置
                    val cutout = windowInsets.displayCutout
                    if (cutout != null) {
                        val rectList = cutout.boundingRects
                        notchRectListener.onResult(rectList)
                    }
                }
            }
        }
    }

    interface GetNotchRectListener {
        fun onResult(rectList: List<Rect>)
    }

}