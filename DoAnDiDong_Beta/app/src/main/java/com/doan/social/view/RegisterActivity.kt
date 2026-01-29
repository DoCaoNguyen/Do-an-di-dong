package com.doan.social.view

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.doan.social.R
import com.doan.social.model.RegisterRequest
import com.doan.social.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class RegisterActivity : AppCompatActivity() {
    private lateinit var edtUsername: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var edtBirthday: EditText
    private lateinit var btnRegRegister: Button
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        edtUsername = findViewById(R.id.edt_Username)
        edtEmail = findViewById(R.id.edt_Email)
        edtPassword = findViewById(R.id.edt_Password)
        edtConfirmPassword = findViewById(R.id.edt_RetypePassword)
        edtBirthday = findViewById(R.id.txtDOB)
        btnRegRegister = findViewById(R.id.btn_RegRegister)

        //DatePicker Ngày sinh
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val d = c.get(Calendar.DAY_OF_MONTH)
        val registerViewModel: RegisterViewModel = RegisterViewModel(client)


        findViewById<EditText>(R.id.txtDOB).setOnClickListener {
            val dpd = DatePickerDialog(this ,R.style.DialogTheme,{_, y:Int, m:Int, d:Int ->
                findViewById<EditText>(R.id.txtDOB).setText("$d/${m + 1}/$y")
            }, y,m,d)
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis())
            dpd.show()
        }

        findViewById<Button>(R.id.btn_RegRegister).setOnClickListener {

            if (edtUsername.text.toString().isEmpty() || edtEmail.text.toString().isEmpty() || edtPassword.text.toString().isEmpty() || edtConfirmPassword.text.toString().isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Vui lòng nhập đầy đủ thông tin")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                return@setOnClickListener
            }

            if (edtConfirmPassword.text.toString() != edtPassword.text.toString()) {
                AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Mật khẩu không khớp")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                return@setOnClickListener
            }

            if (edtPassword.text.toString().length < 8 || edtConfirmPassword.text.toString().length > 16) {
                AlertDialog.Builder(this)
                    .setTitle("Lỗi")
                    .setMessage("Mật khẩu phải từ 8 đến 16 ký tự bao gồm chữ cái, chữ số và ký tự đặc biệt")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                return@setOnClickListener
            }

            val request = RegisterRequest(
                username = edtUsername.text.toString(),
                email = edtEmail.text.toString(),
                password = edtPassword.text.toString(),
                birthday = edtBirthday.text.toString()
            )

            lifecycleScope.launch {
                try {
                    val response = registerViewModel.postRegister(request)

                    if (response?.status == "success") {
                        AlertDialog.Builder(this@RegisterActivity)
                            .setTitle("Thông báo")
                            .setMessage("Đăng ký thành công")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                                finish()
                            }
                            .show()
                    } else {
                        AlertDialog.Builder(this@RegisterActivity)
                            .setTitle("Lỗi")
                            .setMessage("Đăng ký thất bại")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                } catch (e: Exception) {
                    Log.d("Register Error", "${e.message}")
                }
            }

        }

    }
}