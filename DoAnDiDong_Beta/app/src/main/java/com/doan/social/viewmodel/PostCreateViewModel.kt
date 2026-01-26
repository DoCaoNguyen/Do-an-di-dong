package com.doan.social.viewmodel

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class PostCreateViewModel(private val client: OkHttpClient,
                          private val json: Json = Json { ignoreUnknownKeys = true }
) {


}