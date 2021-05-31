package com.bram3r.androidexercise.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {
    private val userService: APIService

    fun getUserService(): APIService {
        return userService
    }

    companion object {
        var instance: UserRepository? = null
            get() {
                if (field == null) {
                    field = UserRepository()
                }
                return field
            }
            private set
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://hello-world.innocv.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userService = retrofit.create(APIService::class.java)
    }
}