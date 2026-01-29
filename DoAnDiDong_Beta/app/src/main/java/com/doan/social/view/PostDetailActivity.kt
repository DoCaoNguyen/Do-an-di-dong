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
import com.doan.social.model.CommentModel
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

        val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        val avatarUrl = sharedPref.getString("avatar", "")
        android.util.Log.d("AVATAR_DEBUG", "URL: $avatarUrl")
        val rcvPostDetail = findViewById<RecyclerView>(R.id.rcvPostDetail)

        lifecycleScope.launch {
            val postJson = intent.getStringExtra("post_data")
            if (postJson != null) {
                val post = Json.decodeFromString<PostModel>(postJson)
                val commentList = postViewModel.getCommentsByPost(post.id)
                val totalCount = calculateTotalComments(commentList)
                post.comments_count = totalCount
                rcvPostDetail.adapter = PostDetailAdapter(post, commentList, avatarUrl, object : PostDetailAdapter.OnClickPostItem {
                    override fun onPostComment(text: String) {
                        lifecycleScope.launch {
                            val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
                            val token = sharedPref.getString("access_token", "")
                            val success = postViewModel.postComment(post!!.id, text, token)

                            if (success) {
                                Toast.makeText(this@PostDetailActivity, "Đã đăng bình luận", Toast.LENGTH_SHORT).show()
                                val updatedComments = postViewModel.getCommentsByPost(post!!.id)
                                commentList.clear()
                                commentList.addAll(updatedComments)
                                rcvPostDetail.adapter?.notifyDataSetChanged()
                            } else {
                                Toast.makeText(this@PostDetailActivity, "Lỗi khi đăng bình luận", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
            else {
                Toast.makeText(this@PostDetailActivity, "Không tìm thấy bài viết", Toast.LENGTH_SHORT).show()
            }
            rcvPostDetail.layoutManager = LinearLayoutManager(this@PostDetailActivity)
        }

    }
}

fun calculateTotalComments(comments: List<CommentModel>): Int {
    var count = 0
    for (comment in comments) {
        count++
        count += calculateTotalComments(comment.replies)
    }
    return count
}