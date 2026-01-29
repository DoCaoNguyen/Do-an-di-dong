package com.doan.social.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.doan.social.R
import com.doan.social.viewmodel.ForgotPasswordViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class ForgotPasswordActivity : AppCompatActivity() {

    private val viewModel = ForgotPasswordViewModel()
    private var resetToken: String? = null
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

<<<<<<< HEAD
        val btnContinue = findViewById<Button>(R.id.btnForgotContinue)
        val edtEmail = findViewById<EditText>(R.id.txtForgotEmail)
        val layoutResetDetails = findViewById<LinearLayout>(R.id.layoutResetDetails)
        val edtOTP = findViewById<EditText>(R.id.edtOTP)
        val edtNewPassword = findViewById<EditText>(R.id.edtNewPassword)
        val txtTitle = findViewById<TextView>(R.id.textView4)
        val txtTimer = TextView(this).apply {
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setPadding(0, 20, 0, 0)
=======
        findViewById<Button>(R.id.btn_Login).setOnClickListener {
            val email = findViewById<EditText>(R.id.txtForgotEmail).text
>>>>>>> 9b678474de3189cfeb55ceec1136ea8955777c11
        }
        layoutResetDetails.addView(txtTimer)

        btnContinue.setOnClickListener {
            val email = edtEmail.text.toString().trim()

            lifecycleScope.launch {
                setLoading(btnContinue, true)

                if (resetToken == null) {
                    val token = viewModel.sendOTP(email)
                    if (token != null) {
                        resetToken = token
                        layoutResetDetails.visibility = View.VISIBLE
                        edtEmail.isEnabled = false
                        txtTitle.text = "Xác thực OTP"
                        startTimer(txtTimer)
                        Toast.makeText(this@ForgotPasswordActivity, "OTP đã được gửi!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ForgotPasswordActivity, "Gửi OTP thất bại", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // --- STEP 2: Verify and Reset ---
                    val otp = edtOTP.text.toString().trim()
                    val newPass = edtNewPassword.text.toString().trim()

                    val isSuccess = viewModel.verifyAndReset(otp, newPass, resetToken!!)
                    if (isSuccess) {
                        countDownTimer?.cancel()
                        Toast.makeText(this@ForgotPasswordActivity, "Đổi mật khẩu thành công!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@ForgotPasswordActivity, "Mã sai hoặc hết hạn", Toast.LENGTH_SHORT).show()
                    }
                }
                setLoading(btnContinue, false) // End loading state
            }
        }
    }

    private fun setLoading(button: Button, isLoading: Boolean) {
        button.isEnabled = !isLoading
        button.text = if (isLoading) "Đang xử lý..." else "Tiếp Tục"
    }

    private fun startTimer(timerTextView: TextView) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timerTextView.text = String.format(Locale.getDefault(), "Mã hết hạn sau: %02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerTextView.text = "Mã OTP đã hết hạn. Vui lòng gửi lại."
                resetToken = null
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}