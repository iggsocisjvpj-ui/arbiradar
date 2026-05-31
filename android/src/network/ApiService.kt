package com.arbiradar.mobile.network

import retrofit2.http.*
import com.arbiradar.mobile.data.User
import com.arbiradar.mobile.data.AuthResponse

interface ApiService {
    
    @POST("auth/register")
    suspend fun register(@Body user: User): AuthResponse
    
    @POST("auth/login")
    suspend fun login(@Body credentials: LoginCredentials): AuthResponse
    
    @GET("auth/refresh")
    suspend fun refreshToken(): AuthResponse
    
    @GET("users/profile")
    suspend fun getProfile(): User
    
    @PUT("users/profile")
    suspend fun updateProfile(@Body user: User): User
    
    @GET("data/list")
    suspend fun getDataList(@Query("page") page: Int): DataListResponse
    
    @GET("data/{id}")
    suspend fun getDataById(@Path("id") id: String): DataItem
    
    @POST("data/create")
    suspend fun createData(@Body data: DataItem): DataItem
    
    @PUT("data/{id}")
    suspend fun updateData(@Path("id") id: String, @Body data: DataItem): DataItem
    
    @DELETE("data/{id}")
    suspend fun deleteData(@Path("id") id: String): DeleteResponse
}

data class LoginCredentials(
    val email: String,
    val password: String
)

data class DataListResponse(
    val data: List<DataItem>,
    val total: Int,
    val page: Int
)

data class DataItem(
    val id: String?,
    val title: String,
    val description: String,
    val createdAt: String?
)

data class DeleteResponse(
    val success: Boolean,
    val message: String
)