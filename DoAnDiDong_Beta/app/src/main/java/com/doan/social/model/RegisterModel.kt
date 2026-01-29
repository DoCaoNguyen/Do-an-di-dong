package com.doan.social.model

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val birthday: String
)

@Serializable
data class RegisterModel(
    val status: String,
    val data: MessageData
)
@Serializable
data class MessageData(
    val message: String
)