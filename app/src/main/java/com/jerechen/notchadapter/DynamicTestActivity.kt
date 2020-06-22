package com.jerechen.notchadapter

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.jerechen.notchadapter.utils.ScreenUtil
import kotlinx.android.synthetic.main.activity_dynamic_test.*

/**
 * @author jere
 */
class DynamicTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置Activity全屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_dynamic_test)

        NotchManager.getNotchScreen()?.getNotchInfo(this, object : INotchScreen.NotchInfoCallback {
            override fun getNotchRect(notchRectInfo: Rect) {
                val topTvLp = topTv.layoutParams as ConstraintLayout.LayoutParams
                val leftTvLp = leftTv.layoutParams as ConstraintLayout.LayoutParams
                //竖屏
                if (ScreenUtil.isPortrait(this@DynamicTestActivity)) {
                    topTvLp.topMargin += notchRectInfo.bottom
                    topTv.layoutParams = topTvLp
                } else {
                    leftTvLp.leftMargin += notchRectInfo.right
                    leftTv.layoutParams = leftTvLp
                }
            }

        })
    }
}