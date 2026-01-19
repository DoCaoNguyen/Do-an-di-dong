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
import kotlinx.coroutines.launch

class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }
        val rcv_postdetail = findViewById<RecyclerView>(R.id.rcvPostDetail)
        lifecycleScope.launch {
//            commentList = commentViewModel.getComment()
            rcv_postdetail.layoutManager = LinearLayoutManager(this@PostDetailActivity)
//            rcv_postdetail.adapter = HomeAdapter(commentList)

        }


    }
}