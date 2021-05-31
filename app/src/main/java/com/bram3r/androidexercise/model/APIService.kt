package com.bram3r.androidexercise.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    // Recoger users
    @GET("/api/User")
    suspend fun getUsers(): Response<List<User>>

    // Crear user
    @POST("/api/User")
    suspend fun createUser(@Body user: User): Call<ResponseBody>

    // Eliminar user
    @DELETE("/api/User/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Call<ResponseBody>

    // Actualizar user
    @PUT("/api/User")
    suspend fun updateUser(@Body user: User): Call<ResponseBody>
}