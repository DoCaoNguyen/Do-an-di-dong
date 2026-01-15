package com.doan.social.view

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.doan.social.R

class RegisterActivity : AppCompatActivity() {
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


        //DatePicker Ngày sinh
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val d = c.get(Calendar.DAY_OF_MONTH)

        findViewById<EditText>(R.id.txtDOB).setOnClickListener {
            val dpd = DatePickerDialog(this ,R.style.DialogTheme,{_, y:Int, m:Int, d:Int ->
                findViewById<EditText>(R.id.txtDOB).setText("$d/${m + 1}/$y")
            }, y,m,d)
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis())
            dpd.show()
        }

        findViewById<Button>(R.id.btnRegRegister).setOnClickListener {

            //luu

            finish()
        }
        findViewById<ImageButton>(R.id.imgbtnBack2).setOnClickListener {
            finish()
        }


    }
}