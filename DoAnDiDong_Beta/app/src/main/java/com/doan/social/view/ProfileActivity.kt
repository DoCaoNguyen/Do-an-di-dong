package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.doan.social.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private lateinit var img_setting_profile: ImageView
    private lateinit var btn_follow: Button

    private lateinit var img_home: ImageView
    private lateinit var img_search: ImageView
    private lateinit var img_notification: ImageView
    private lateinit var img_user: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        img_setting_profile = findViewById(R.id.img_setting_profile)
        img_setting_profile.setOnClickListener {
            val intent = Intent(this, SettingProfileActivity::class.java)
            startActivity(intent)
        }

        btn_follow = findViewById(R.id.btn_follow)
        btn_follow.setBackgroundResource(R.drawable.btn_selected)
        btn_follow.setOnClickListener {
            //Khi click vào thì +following vào csdl
            btn_follow.isSelected = !btn_follow.isSelected
            if (btn_follow.isSelected){
                btn_follow.setText("Đã Follow")
            }else{
                btn_follow.setText("Follow")
            }
        }

        val botNav = findViewById<BottomNavigationView>(R.id.btnNavi)
        botNav.setSelectedItemId(R.id.bottom_profile)

        botNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> {
                    finish()
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.bottom_search -> {
                    finish()
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.bottom_notification -> {
                    finish()
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }
                R.id.bottom_profile -> {
                    true
                }
                else -> false
            }
        }
    }
}