package com.doan.social.viewmodel

import android.util.Log
import com.doan.social.model.PostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.OkHttpClient
import okhttp3.Request

class HomeViewModel {
    private val baseUrl = "http://10.0.2.2:3000/api/posts"
    private val client = OkHttpClient()
    suspend fun getPost(): MutableList<PostModel> {
        val postList = mutableListOf<PostModel>()
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(baseUrl)
                    .get()
                    .build()

                val response = client.newCall(request).execute()

                response.use { res ->
                    val bodyString = res.body?.string() ?: ""
                    Log.d("data", bodyString)

                    if (res.isSuccessful) {
                        val json = Json { ignoreUnknownKeys = true }
                        val element = json.parseToJsonElement(bodyString)
                        val postsArray =
                            element.jsonObject["data"]?.jsonObject?.get("posts")?.jsonArray

                        if (postsArray != null) {
                            val data = json.decodeFromJsonElement<List<PostModel>>(postsArray)
                            postList.addAll(data)
                        }
                    } else {
                        Log.e("API_ERROR", "Status Code: ${res.code}")
                    }
                }
            } catch (e: Exception) {
                Log.e("NETWORK_ERROR", e.message ?: "Unknown error")
            }
            postList
        }
    }
}