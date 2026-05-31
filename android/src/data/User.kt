package com.arbiradar.mobile.data

data class User(
    val id: String? = null,
    val email: String,
    val password: String? = null,
    val name: String,
    val phone: String? = null,
    val avatar: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
    val user: User? = null
)