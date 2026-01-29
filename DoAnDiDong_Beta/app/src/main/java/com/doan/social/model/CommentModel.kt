package com.doan.social.model

import kotlinx.serialization.Serializable

@Serializable
data class CommentModel(
    val id: Int,
    val comment: String,
    val post_id: Int,
    val users_id: Int,
    val parent_id: Int?,
    val user: UserPost
)