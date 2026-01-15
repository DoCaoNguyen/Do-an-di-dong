package com.doan.social.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String,
    val password: String
)

@Serializable
data class User_model(
    val status: String,
    val data: Users
)

@Serializable
data class Users(
    val user: UserData,
    val accessToken: String
)
@Serializable
data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val role: String,
    val avatarurl: String?, //null chưa upload
    val gender: String?,//null chưa upload
    val phone: String?,//null chưa upload
    val birthday: String?,//null chưa upload
    val status: String,
    val interests: List<String>
)
