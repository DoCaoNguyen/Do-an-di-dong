package com.doan.social.model
import kotlinx.serialization.Serializable

@Serializable
data class LogoutModel(
    val status: String,
    val message: String
)
