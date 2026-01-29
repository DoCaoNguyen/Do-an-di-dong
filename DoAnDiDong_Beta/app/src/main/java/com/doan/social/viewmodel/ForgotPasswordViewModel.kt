package com.doan.social.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ForgotPasswordViewModel {
    val client = okhttp3.OkHttpClient()
    suspend fun sendOTP(email: String): String? {
        val url = "http://10.0.2.2:3000/api/auth/forgot-password"
        val jsonBody = "{\"email\": \"$email\"}"
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).post(requestBody).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: ""
                    val jsonElement = Json.parseToJsonElement(body).jsonObject
                    jsonElement["resetToken"]?.jsonPrimitive?.content
                } else null
            } catch (e: Exception) { null }
        }
    }

    suspend fun verifyAndReset(otp: String, newPass: String, resetToken: String): Boolean {
        val url = "http://10.0.2.2:3000/api/auth/reset-password"
        val jsonBody = """
        {
            "otp": "$otp",
            "newPassword": "$newPass",
            "resetToken": "$resetToken"
        }
    """.trimIndent()
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).post(requestBody).build()
                client.newCall(request).execute().isSuccessful
            } catch (e: Exception) { false }
        }
    }
}