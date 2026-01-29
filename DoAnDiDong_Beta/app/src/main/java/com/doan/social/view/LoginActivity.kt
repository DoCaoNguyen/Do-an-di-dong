package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.doan.social.R
import com.doan.social.model.UserRequest
import com.doan.social.viewmodel.UserViewmodel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import androidx.core.content.edit
import kotlinx.coroutines.delay

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText

    private lateinit var edtPassword: EditText

    private val client = OkHttpClient()

    private var failCount = 0

    private var isLocked = false

    private fun lockLoginButton() {
        val btnLogin = findViewById<Button>(R.id.btn_Login)

        isLocked = true
        btnLogin.isEnabled = false

        lifecycleScope.launch {
            delay(30000)
            failCount = 0
            isLocked = false
            btnLogin.isEnabled = true
        }
    }



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
        val onboardprefs = getSharedPreferences("onboarding_done", MODE_PRIVATE)
        edtEmail = findViewById(R.id.txtForgotEmail)
        edtPassword = findViewById(R.id.edtRegPassword)
        val userViewmodel: UserViewmodel = UserViewmodel(client)
        var user: UserRequest
        findViewById<Button>(R.id.btn_Login).setOnClickListener {

            if (isLocked) {
                return@setOnClickListener
            }

            lifecycleScope.launch {
                user = UserRequest(edtEmail.text.toString(), edtPassword.text.toString())
                val userData = userViewmodel.postLogin(user)
                val userdata = getSharedPreferences("user_data", MODE_PRIVATE)
                if (userData?.data?.accessToken!= null) {
                    onboardprefs.edit { putBoolean("onboarding_done", true) }
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                        userdata.edit {
                            putString("accessToken", userData.data.accessToken)
                                .putInt("userid", userData.data.user.id)
                        }
                    }
                    startActivity(intent)
                } else {

                    failCount++

                    if (failCount <= 5 ){
                        Toast.makeText(this@LoginActivity, "Sai email đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        Toast.makeText(
                            this@LoginActivity,
                            "Sai tài khoản hoặc mật khẩu quá 5 lần. Vui lòng chờ 30 giây để thử lại",
                            Toast.LENGTH_SHORT
                        ).show()

                        if (failCount >= 5) {
                            lockLoginButton()
                            failCount = 0
                        }
                    }
                }

            }
        }
    }
}
