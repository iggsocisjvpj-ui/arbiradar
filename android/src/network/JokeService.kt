package com.arbiradar.mobile.network

import retrofit2.http.*

data class JokeResponse(
    val id: Int? = null,
    val type: String? = null,
    val setup: String? = null,
    val delivery: String? = null,
    val joke: String? = null,
    val safe: Boolean? = null,
    val error: Boolean? = null
)

data class JokeListResponse(
    val jokes: List<JokeResponse>,
    val total: Int
)

interface JokeService {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeResponse
    
    @GET("jokes/category/{category}")
    suspend fun getJokeByCategory(@Path("category") category: String): JokeResponse
    
    @GET("jokes/search")
    suspend fun searchJokes(@Query("q") query: String): JokeListResponse
    
    @POST("jokes/favorites")
    suspend fun addFavorite(@Body joke: JokeResponse): JokeResponse
    
    @GET("jokes/favorites")
    suspend fun getFavorites(): JokeListResponse
    
    @DELETE("jokes/favorites/{id}")
    suspend fun removeFavorite(@Path("id") id: Int): DeleteResponse
}

data class DeleteResponse(
    val success: Boolean,
    val message: String
)
