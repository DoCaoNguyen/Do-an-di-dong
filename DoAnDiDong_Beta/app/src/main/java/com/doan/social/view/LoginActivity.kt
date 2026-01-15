package com.doan.social.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.doan.social.model.User_model
import com.doan.social.viewmodel.UserViewmodel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    private lateinit var btnForgotNext: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText

    private val client = OkHttpClient()



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
            pref.edit { putBoolean("onboarding_done", true) }
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btnForgotNext = findViewById(R.id.btnForgotNext)
        edtEmail = findViewById(R.id.edtRegEmail)
        edtPassword = findViewById(R.id.edtRegPassword)
        val userViewmodel: UserViewmodel = UserViewmodel(client)
        var user: UserRequest

        btnForgotNext.setOnClickListener {
            lifecycleScope.launch {
                user = UserRequest(edtEmail.text.toString(), edtPassword.text.toString())
                val userData = userViewmodel.postLogin(user)
                val userdata = getSharedPreferences("user_data", MODE_PRIVATE)
                if (userData?.data?.accessToken!= null) {

                    val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                        userdata.edit {
                            putString("accessToken", userData.data.accessToken)
                                .putInt("userid", userData.data.user.id)
                        }
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }
}
