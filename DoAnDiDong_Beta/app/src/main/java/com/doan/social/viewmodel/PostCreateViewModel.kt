package com.doan.social.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import com.doan.social.model.PostCreateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class PostCreateViewModel(private val client: OkHttpClient,
                          private val json: Json = Json { ignoreUnknownKeys = true }
) {
    suspend fun postCreatePost(accessToken: String?,
                               title: String,
                               content: String,
                               imageUri: Uri?,
                               tag: String,
                               contentResolver: ContentResolver
    ): PostCreateModel {
        try {
            val baseUrl = "http://10.0.2.2:3000/api/posts/create"

            val bodyBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("tag", tag)

            if (imageUri != null) {
                val fileName = getFileNameFromUri(contentResolver, imageUri)
                val imageBody = uriToRequestBody(contentResolver, imageUri)

                bodyBuilder.addFormDataPart(
                    "image",
                    fileName,
                    imageBody
                )
            }

            val requestBody = bodyBuilder.build()
            val request = Request.Builder()
                .url(baseUrl)
                .post(requestBody)
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            val response = withContext(Dispatchers.IO) {

                client.newCall(request).execute()
            }

            response.use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: ""
                    return json.decodeFromString<PostCreateModel>(body)
                } else {
                    throw Exception("Lỗi Craete Post: ${response.code} - ${response.message}")
                }
            }
        }catch (e: Throwable){
            throw Exception("Lỗi Catch Craete Post: ${e.message}")
        }

    }

    private fun uriToRequestBody(
        resolver: ContentResolver,
        uri: Uri
    ): RequestBody {
        val bytes = resolver.openInputStream(uri)!!.readBytes()
        return bytes.toRequestBody("image/*".toMediaType())
    }

    private fun getFileNameFromUri(
        resolver: ContentResolver,
        uri: Uri
    ): String {
        var name = "upload.jpg"
        val cursor = resolver.query(uri, null, null, null, null)
        cursor?.use {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && index != -1) {
                name = it.getString(index)
            }
        }
        return name
    }

}