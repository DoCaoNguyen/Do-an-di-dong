package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doan.social.R
import com.doan.social.adapter.HomeAdapter
import com.doan.social.model.Post
import com.doan.social.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private var homeViewModel = HomeViewModel()
    private lateinit var postList: MutableList<Post>
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
            postList = homeViewModel.getPost()
            rcv_home.layoutManager = LinearLayoutManager(this@HomeActivity)
            rcv_home.adapter = HomeAdapter(postList)
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
                    finish()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}