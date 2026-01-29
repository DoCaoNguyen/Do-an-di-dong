package com.doan.social.viewmodel


import android.content.ContentResolver
import android.content.SharedPreferences
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.doan.social.model.LogoutModel
import com.doan.social.model.UpdateProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class SettingProfileViewModel(private val client: OkHttpClient,
                              private val prefs: SharedPreferences,
                              private val json: Json = Json { ignoreUnknownKeys = true }
) {

    suspend fun postLogout(accessToken: String?): LogoutModel? {
       try {
            val baseUrl = "http://10.0.2.2:3000/api/auth/logout"
            val request = Request.Builder()
                .url(baseUrl)
                .post("".toRequestBody("application/json".toMediaType()))
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            val response = withContext(Dispatchers.IO){
                client.newCall(request).execute()
            }

            response.use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string() ?:""
                    val logoutResponse = json.decodeFromString<LogoutModel>(body)
                    prefs.edit().clear().apply()
                    return logoutResponse
                } else {
                    return null
                }
            }
        }catch (e: Throwable){
         throw Exception("Lỗi: ${e.message}")
        }
    }

    suspend fun updateProfile(
        accessToken: String?,
        phone: String, gender: String,
        birthday: String,
//        avatar: Uri?,
//        contentResolver: ContentResolver
    ): UpdateProfileModel? {
        try {
            val baseUrl = "http://10.0.2.2:3000/api/users/update"
            val bodyBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phone", phone)
                .addFormDataPart("gender", gender)
                .addFormDataPart("birthday", birthday)

//            if (avatar != null) {
//                val imageBody = uriToRequestBody(contentResolver, avatar)
//
//                bodyBuilder.addFormDataPart(
//                    "image",
//                    "avatar.jpg",
//                    imageBody
//                )
//            }

            val request = Request.Builder()
                .url(baseUrl)
                .put(bodyBuilder.build())
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            val response = withContext(Dispatchers.IO){
                client.newCall(request).execute()
            }

            response.use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string() ?:""
                    val userResponse = json.decodeFromString<UpdateProfileModel>(body)
                    return userResponse
                } else {
                    return null
                }
            }
        }catch (e: Throwable){
            throw Exception("Lỗi: ${e.message}")
        }
    }

//    private fun uriToRequestBody(
//        resolver: ContentResolver,
//        uri: Uri
//    ): RequestBody {
//        val bytes = resolver.openInputStream(uri)!!.readBytes()
//        return bytes.toRequestBody("image/*".toMediaType())
//    }

}


