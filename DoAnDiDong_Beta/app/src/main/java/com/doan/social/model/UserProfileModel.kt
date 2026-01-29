package com.doan.social.model

import kotlinx.serialization.Serializable


@Serializable
data class UserProfileModel(
    val id: Int,
    val username: String,
    val email: String,
    val role: String,
    val avatarurl: String,
    val gender: String,
    val phone: String,
    val birthday: String,
    val status: String,
    val followersCount: Int,
    val followingCount: Int,
    val userTags: List<Tag>
)
