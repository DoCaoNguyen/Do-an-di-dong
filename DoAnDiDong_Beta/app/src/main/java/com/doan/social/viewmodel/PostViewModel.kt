package com.doan.social.viewmodel

import android.util.Log
import com.doan.social.model.CommentModel
import com.doan.social.model.PostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.OkHttpClient
import okhttp3.Request

class PostViewModel {
    private val baseUrl = "http://10.0.2.2:3000/api/posts"
    private val client = OkHttpClient()
    suspend fun getPost(): MutableList<PostModel> {

        val postList = mutableListOf<PostModel>()
        var currentPage = 1
        var hasMore = true
        return withContext(Dispatchers.IO) {
            try {

                while(hasMore) {
                    val url = "$baseUrl?page=$currentPage"
                    val request = Request.Builder()
                        .url(url)
                        .get()
                        .build()
                    val response = client.newCall(request).execute()

                response.use { res ->
                    if (res.isSuccessful) {
                        val bodyString = res.body?.string() ?: ""
                        val json = Json { ignoreUnknownKeys = true }
                        val element = json.parseToJsonElement(bodyString).jsonObject
                        val dataObject = element["data"]?.jsonObject
                        val postsArray =
                            dataObject?.get("posts")?.jsonArray

                        if (postsArray != null) {
                            val posts = json.decodeFromJsonElement<List<PostModel>>(postsArray)
                            postList.addAll(posts)
                        }

                        hasMore = dataObject?.get("hasMore")?.jsonPrimitive?.booleanOrNull ?: false

                        if (hasMore) {
                            currentPage++
                        }

                    } else {
                        hasMore = false
                        Log.e("API_ERROR", "Status Code: ${res.code}")
                    }
                  }
                }
            } catch (e: Exception) {
                Log.e("NETWORK_ERROR", e.message ?: "Unknown error")
            }
            postList
        }
    }

    suspend fun getCommentsByPost(postId: Int): MutableList<CommentModel> {
        val commentModelList = mutableListOf<CommentModel>()
        val url = "${baseUrl}/api/posts/${postId}/comments"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()

                val response = client.newCall(request).execute()

                response.use { res ->
                    if (res.isSuccessful) {
                        val bodyString = res.body?.string() ?: ""
                        val json = Json { ignoreUnknownKeys = true }
                        val element = json.parseToJsonElement(bodyString).jsonObject
                        val commentsArray = element["data"]?.jsonArray

                        if (commentsArray != null) {
                            val data = json.decodeFromJsonElement<List<CommentModel>>(commentsArray)
                            commentModelList.addAll(data)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi lấy bình luận: ${e.message}")
            }
            commentModelList
        }
    }

}