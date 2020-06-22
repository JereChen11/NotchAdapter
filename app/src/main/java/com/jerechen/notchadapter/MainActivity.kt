package com.jerechen.notchadapter

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author jere
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("jereTest", "manufacturer: ${Build.MANUFACTURER}")

        portraitTestBtn.setOnClickListener(this)
        landscapeTestBtn.setOnClickListener(this)
        dynamicTestBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.portraitTestBtn -> {
                val portraitIntent = Intent(this, PortraitTestActivity::class.java)
                startActivity(portraitIntent)
            }
            R.id.landscapeTestBtn -> {
                val landscapeIntent = Intent(this, LandscapeTestActivity::class.java)
                startActivity(landscapeIntent)
            }
            R.id.dynamicTestBtn -> {
                val dynamicIntent = Intent(this, DynamicTestActivity::class.java)
                startActivity(dynamicIntent)
            }
        }
    }
}