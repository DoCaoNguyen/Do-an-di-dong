package com.doan.social.viewmodel

import android.util.Log
import com.doan.social.model.UserRequest
import com.doan.social.model.User_model
import com.doan.social.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class UserViewmodel {
    private val client = OkHttpClient();

    suspend fun postLogin(request: UserRequest):String? {
        try {
            val baseUrl = "http://10.0.2.2:3000/api/auth/login"
            val json = Json { ignoreUnknownKeys = true }

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

            response.use {
                val body = it.body?.string() ?: ""

                if (it.isSuccessful) {
                    val loginResponse = json.decodeFromString<User_model>(body)
                    val token = loginResponse.data.accessToken
                    return token
                } else {
                    return null
                }
            }

        } catch (e: Exception) {
            throw  Exception("Lỗi đăng nhập: ${e.message}")
        }
    }

}