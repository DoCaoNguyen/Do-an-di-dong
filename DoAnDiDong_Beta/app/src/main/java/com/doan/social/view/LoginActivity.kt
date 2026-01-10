package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.doan.social.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        val pref = getSharedPreferences("onboarding_done", MODE_PRIVATE)

        //Chưa có tài khoản -> Đăng ký
        findViewById<TextView>(R.id.btnRegLogin).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        findViewById<TextView>(R.id.btnRegLogin2).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        //Quên mật khẩu
        findViewById<TextView>(R.id.btnForgotPassword).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        //Đăng nhập
        findViewById<Button>(R.id.btnForgotNext).setOnClickListener {
            pref.edit().putBoolean("onboarding_done",true).apply()
            startActivity(Intent(this, HomeActivity::class.java))
        }



    }
}