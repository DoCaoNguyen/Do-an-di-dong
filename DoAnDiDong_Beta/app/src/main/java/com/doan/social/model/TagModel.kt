package com.doan.social.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag (
    val id:Int,
    val tags_name: String
)