package com.doan.social.model
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val title: String?,
    val users_id: Int,
    val content: String?,
    val image_url: String?,
    val status: String,
    val user: UserPost?,
)
