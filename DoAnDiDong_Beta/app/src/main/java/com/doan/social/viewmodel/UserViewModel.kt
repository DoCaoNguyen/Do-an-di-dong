package com.doan.social.viewmodel

import android.R.attr.data
import android.util.Log
import com.doan.social.model.Post
import com.doan.social.model.PostProfileRequest
import com.doan.social.model.UserRequest
import com.doan.social.model.User_model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class UserViewmodel (private val client: OkHttpClient,
                     private val json: Json = Json { ignoreUnknownKeys = true }
){
    suspend fun postLogin(request: UserRequest): User_model? {
        try {
            val baseUrl = "http://10.0.2.2:3000/api/auth/login"
            val jsonBody = json.encodeToString(request)
            val requestBody =
                jsonBody.toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(baseUrl)
                .post(requestBody)
                .build()

            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }

            response.use { response ->
                val code = response.code
                val body = response.body?.string() ?: ""

                if (code in 200..299) {
                    val loginResponse = json.decodeFromString<User_model>(body)
                    return loginResponse
                } else {
                    return null
                }
            }
        } catch (e: Exception) {
            throw  Exception("Lỗi đăng nhập: ${e.message}")
        }
    }

    suspend fun getPostProfile(userid: Int?): MutableList<Post>{

        val postList = mutableListOf<Post>()
        val url = "http://10.0.2.2:3000/api/posts"

        return withContext(Dispatchers.IO){
            try {


        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("HTTP ${response.code}: ${response.message}")
            }

            val bodyString = response.body?.string() ?: ""
            val temp = json.parseToJsonElement(bodyString)
            val array = temp.jsonObject["data"]?.jsonObject?.get("posts")?.jsonArray
            if (array != null) {
                val data = json.decodeFromJsonElement<List<Post>>(array)
                Log.d("data","$data")
                postList.addAll(data.filter { Post -> Post.users_id == userid })
            }else{
            throw Exception("Lỗi đăng nhập: ${response.message}")
                }
        }

        }catch (e: Exception) {
            throw  Exception("Lỗi đăng nhập: ${e.message}")
            }
        postList
        }
    }
}