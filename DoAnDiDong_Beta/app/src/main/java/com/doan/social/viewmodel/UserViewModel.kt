package com.doan.social.viewmodel

import android.util.Log
import com.doan.social.model.PostModel
import com.doan.social.model.UserProfileModel
import com.doan.social.model.UserRequest
import com.doan.social.model.User_model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
                    val json = Json { ignoreUnknownKeys = true }
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
    suspend fun getUserProfile(token: String?): UserProfileModel {
        val baseUrl = "http://10.0.2.2:3000/api/users/me"
        var user: UserProfileModel = UserProfileModel(0,"","","","","","","","",0,0, emptyList())
        return withContext(Dispatchers.IO){
            try {
                val request = Request.Builder()
                    .url(baseUrl)
                    .header("Authorization", "Bearer $token")
                    .get()
                    .build()
                val response = client.newCall(request).execute()
                response.use { res ->
                    val bodyString = res.body?.string() ?: ""
                    if (res.isSuccessful){
                        val element = json.parseToJsonElement(bodyString)
                        val temp = element.jsonObject["data"]?.jsonObject
                        user = json.decodeFromJsonElement<UserProfileModel>(temp!!)
                        Log.d("user",user.toString())
                    } else {
                        Log.e("dataProfile", "Status Code: ${res.code}")
                    }
                }
            } catch (e: Throwable){
                throw Exception("Lỗi đăng nhập: ${e.message}")
            }
            user
        }
    }

    suspend fun getPostProfile(userid: Int): MutableList<PostModel> {
        var postList = mutableListOf<PostModel>()

        val baseUrl = "http://10.0.2.2:3000/api/posts/user/${userid}"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(baseUrl)
                    .get()
                    .build()

                val response = client.newCall(request).execute()

                response.use { res ->
                    val bodyString = res.body?.string() ?: ""

                    if (res.isSuccessful) {
                        val json = Json { ignoreUnknownKeys = true }
                        val element = json.parseToJsonElement(bodyString)
                        val postsArray =
                            element.jsonObject["data"]?.jsonObject?.get("posts")?.jsonArray
                        Log.d("postsArray",postsArray.toString())

                        if (postsArray != null) {
                            val data = json.decodeFromJsonElement<List<PostModel>>(postsArray)
                            postList.addAll(data)
                        }
                    } else {
                        Log.e("postProfile", "Status Code: ${response.code}")
                    }
                }
            } catch (e: Exception) {
                Log.e("postProfile", e.message ?: "Unknown error")
            }
            postList
        }
    }
}