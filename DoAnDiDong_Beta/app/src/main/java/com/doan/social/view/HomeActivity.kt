package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doan.social.R
import com.doan.social.adapter.HomeAdapter
import com.doan.social.model.PostModel
import com.doan.social.viewmodel.PostViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HomeActivity : AppCompatActivity() {

    private var postViewModel = PostViewModel()
    private lateinit var postList: MutableList<PostModel>
    private lateinit var rcv_home: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        rcv_home = findViewById(R.id.rcvHome)
        lifecycleScope.launch {
            postList = postViewModel.getPost()
            rcv_home.layoutManager = LinearLayoutManager(this@HomeActivity)
            rcv_home.adapter = HomeAdapter(postList, object : HomeAdapter.OnClickPostItem {
                override fun onClickPostItem(post: PostModel) {
                    val intent = Intent(this@HomeActivity, PostDetailActivity::class.java)
                    val postJson = Json.encodeToString(post)
                    intent.putExtra("post_data",postJson)
                    startActivity(intent)
                }
            })
        }
        val userdata = getSharedPreferences("user_data", MODE_PRIVATE)
        val accessToken = userdata.getString("accessToken", "")


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
                    finish()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}