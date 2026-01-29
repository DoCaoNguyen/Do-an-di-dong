package com.doan.social.viewmodel

import android.util.Log
import com.doan.social.model.RegisterModel
import com.doan.social.model.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterViewModel(private val client: OkHttpClient,
                        private val json: Json = Json { ignoreUnknownKeys = true }
) {
    suspend fun postRegister(request: RegisterRequest): RegisterModel?{
        try {
            val baseUrl = "http://10.0.2.2:3000/api/auth/register"

            val jsonBody = json.encodeToString(request)
            val requestBody =
                jsonBody.toRequestBody("application/json".toMediaType())

            val httpRequest = Request.Builder()
                .url(baseUrl)
                .post(requestBody)
                .build()

            val response = withContext(Dispatchers.IO) {
                client.newCall(httpRequest).execute()
            }

            response.use { res ->
                val code = res.code
                val body = res.body?.string() ?: ""

                if (code in 200..299){
                    val registerResponse = json.decodeFromString<RegisterModel>(body)
                    return registerResponse
                }else{
                    return null
                    Log.d("register", "Status Code: ${res.code}")
                }
            }
        }catch (e: Throwable){
            throw Exception("Lỗi: ${e.message}")

        }
    }
}