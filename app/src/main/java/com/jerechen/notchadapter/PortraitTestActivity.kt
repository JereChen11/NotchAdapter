package com.jerechen.notchadapter

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_portrait_test.*

/**
 * @author jere
 */
class PortraitTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置Activity全屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_portrait_test)

        val notchScreen = NotchManager.getNotchScreen()
        val isContainNotch = notchScreen?.isContainNotch(this)
        Log.e("jereTest", "portrait activity isContainNotch : $isContainNotch")
        notchScreen?.getNotchInfo(this, object : INotchScreen.NotchInfoCallback {
            override fun getNotchRect(notchRectInfo: Rect) {
                Log.e("jereTest", "Rect Bottom : ${notchRectInfo.bottom}")
                //将被刘海挡住的 portraitTitleTv 向下移动一个 刘海高度 距离
                val lp: ConstraintLayout.LayoutParams =
                    portraitTitleTv.layoutParams as ConstraintLayout.LayoutParams
                //在原有的 topMargin 基础上再加上 刘海屏的高度
                lp.topMargin += notchRectInfo.bottom
                portraitTitleTv.layoutParams = lp
            }

        })


    }

}