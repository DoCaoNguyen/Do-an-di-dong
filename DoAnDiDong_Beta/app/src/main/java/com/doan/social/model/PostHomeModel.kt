package com.doan.social.model

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val status: String,
    val data: PostData
)

@Serializable
data class PostData(
    val posts: List<PostModel>,
    val totalItems: Int,
    val currentPage: Int,
    val hasMore: Boolean,
    val nextPage: Int
)

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
)

@Serializable
data class UserPost(
    val id: Int,
    val username: String,
    val avatarurl: String? = null
)

@Serializable
data class Tag(
    val id: Int,
    val tags_name: String
)
