package com.doan.social.model
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String?,
    val imgUrl: String? = null
)
