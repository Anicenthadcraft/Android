package com.ancient.ancient_handcraft.feature.ForgotPassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ancient.ancient_handcraft.R
import kotlinx.android.synthetic.main.toolbar_layout.*

class ForgotPasswordActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_activity)
        initView()
    }

    private fun initView() {
        tv_header.text = "Forgot Password"
    }
}