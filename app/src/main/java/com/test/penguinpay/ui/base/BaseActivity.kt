package com.test.penguinpay.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.penguinpay.R

abstract class BaseActivity: AppCompatActivity() {

    private var backPressed: Long = 0L

    override fun onBackPressed() {
        if (!isTaskRoot || backPressed + DOUBLE_BACK_TIME > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            backPressed = System.currentTimeMillis()
            Toast.makeText(this, getString(R.string.msg_exit), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val DOUBLE_BACK_TIME = 2000L
    }
}