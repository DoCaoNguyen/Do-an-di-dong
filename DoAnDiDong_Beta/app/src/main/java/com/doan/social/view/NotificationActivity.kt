package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.doan.social.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        val botNav = findViewById<BottomNavigationView>(R.id.btnNavi)
        botNav.setSelectedItemId(R.id.bottom_notification)

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
                    true
                }
                R.id.bottom_profile -> {
                    finish()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}