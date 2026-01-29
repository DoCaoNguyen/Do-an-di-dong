package com.doan.social.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doan.social.R
import com.doan.social.adapter.PostDetailAdapter
import com.doan.social.model.PostModel
import com.doan.social.viewmodel.PostViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class PostDetailActivity : AppCompatActivity() {
    private val postViewModel = PostViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        val rcvPostDetail = findViewById<RecyclerView>(R.id.rcvPostDetail)

        lifecycleScope.launch {
            val postJson = intent.getStringExtra("post_data")
            if (postJson != null) {
                val post = Json.decodeFromString<PostModel>(postJson)
                val commentList = postViewModel.getCommentsByPost(post.id)
                rcvPostDetail.adapter = PostDetailAdapter(post, commentList)
            }
            else {
                Toast.makeText(this@PostDetailActivity, "Không tìm thấy bài viết", Toast.LENGTH_SHORT).show()
            }
            rcvPostDetail.layoutManager = LinearLayoutManager(this@PostDetailActivity)
        }
    }
}