package com.doan.social.model
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val title: String?,
    val content: String?,
    val user: User?
)