package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.doan.social.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        val botNav = findViewById<BottomNavigationView>(R.id.btnNavi)
        botNav.setSelectedItemId(R.id.bottom_home)

        botNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> {
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
                    var accessToken = intent.getStringExtra("accessToken")
                    var userid = intent.getIntExtra("id",0)
                    Log.d("userid","$userid")
                    var userName = intent.getStringExtra("username")
                    var userGender = intent.getStringExtra("gender")
                    var userPhone = intent.getStringExtra("phone")
                    var userBirthday = intent.getStringExtra("birthday")
                    finish()
                    val intent = Intent(this, ProfileActivity::class.java)

                    intent.putExtra("accessToken", accessToken)
                    intent.putExtra("username", userName)
                    intent.putExtra("gender", userGender)
                    intent.putExtra("phone", userPhone)
                    intent.putExtra("birthday", userBirthday)
                    intent.putExtra("id", userid)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}