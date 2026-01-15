package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doan.social.R
import com.doan.social.adapter.PostProfileAdapter
import com.doan.social.model.Post
import com.doan.social.viewmodel.UserViewmodel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class ProfileActivity : AppCompatActivity(), PostProfileAdapter.OnClickPostItem {
    private lateinit var img_setting_profile: ImageView
    private lateinit var txt_userName: TextView
    private lateinit var txt_userGender: TextView
    private lateinit var txt_userPhone: TextView
    private lateinit var txt_userBirthday: TextView
    private lateinit var rcv_postProfile: RecyclerView
    private val client = OkHttpClient()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        txt_userName =  findViewById(R.id.txt_userName)
        txt_userGender =  findViewById(R.id.txt_gender)
        txt_userPhone =  findViewById(R.id.txt_phone)
        txt_userBirthday =  findViewById(R.id.txt_birthday)
        rcv_postProfile = findViewById(R.id.rcv_postProfile)
        img_setting_profile = findViewById(R.id.img_setting_profile)

        txt_userName.setText(intent.getStringExtra("username"))
        txt_userGender.setText(intent.getStringExtra("gender"))
        txt_userPhone.setText(intent.getStringExtra("phone"))
        txt_userBirthday.setText(intent.getStringExtra("birthday"))

        img_setting_profile.setOnClickListener {
            val intent = Intent(this, SettingProfileActivity::class.java)
            startActivity(intent)
        }

        val userId = intent.getIntExtra("id",0)
        val postView = UserViewmodel(client)
        var listPost: MutableList<Post> = mutableListOf<Post>()

        lifecycleScope.launch {
            listPost =  postView.getPostProfile(userId)
            rcv_postProfile.layoutManager = LinearLayoutManager(this@ProfileActivity)
            rcv_postProfile.adapter = PostProfileAdapter(listPost,this@ProfileActivity)
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

    override fun onClickPostItem(post: Int) {
        TODO("Not yet implemented")
    }
}