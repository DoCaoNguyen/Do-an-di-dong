package com.doan.social.model

import kotlinx.serialization.Serializable


@Serializable
data class PostCreateModel(
    val status: String,
    val data: PostModel,
)

@Serializable
data class PostDataModel(
    val id: Int,
    val title: String,
    val users_id: Int,
    val content: String,
    val image_url: String,
    val status: String,
    val tags: List<TagCreate>
)

@Serializable
data class TagCreate(
    val id: Int,
    val tags_name: String
)