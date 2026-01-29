package com.doan.social.model
import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    val id: Int,
    val title: String,
    val users_id: Int,
    val content: String?,
    val image_url: String?,
    val status: String,
    val user: UserPost,
    val tags: List<Tag>,
    var isExpanded: Boolean = false
)

@Serializable
data class UserPost(
    val username: String?,
    val avatarurl: String? = null
)

