package com.jerechen.notchadapter

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_landscape_test.*

/**
 * @author jere
 */
class LandscapeTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置Activity全屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_landscape_test)

        //set landscape
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        val notchScreen = NotchManager.getNotchScreen()
        val isContainNotch =  notchScreen?.isContainNotch(this)
        Log.e("jereTest", "portrait activity isContainNotch : $isContainNotch")
        notchScreen?.getNotchInfo(this, object : com.jerechen.notchadapter.INotchScreen.NotchInfoCallback {
            override fun getNotchRect(notchRectInfo: Rect) {
                Log.e("jereTest", "Rect left : ${notchRectInfo.left}")
                Log.e("jereTest", "Rect top : ${notchRectInfo.top}")
                Log.e("jereTest", "Rect right : ${notchRectInfo.right}")
                Log.e("jereTest", "Rect bottom : ${notchRectInfo.bottom}")
                //将被刘海挡住的 portraitTitleTv 向下移动一个 刘海高度 距离
                val lp : ConstraintLayout.LayoutParams = landscapeTitleTv.layoutParams as ConstraintLayout.LayoutParams
                lp.leftMargin += notchRectInfo.right
                landscapeTitleTv.layoutParams = lp
            }

        })

    }

}