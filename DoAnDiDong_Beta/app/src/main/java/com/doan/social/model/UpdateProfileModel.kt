package com.doan.social.model

import kotlinx.serialization.Serializable


@Serializable
data class UpdateProfileModel(
    val status: String,
    val message: String,
    val data: UserUpdateAffter
)
@Serializable
data class UserUpdateAffter(
    val id: Int,
    val username: String,
    val email: String,
    val role: String,
    val avatarurl: String?,
    val gender: String,
    val phone: String,
    val birthday: String,
    val status: String
)
