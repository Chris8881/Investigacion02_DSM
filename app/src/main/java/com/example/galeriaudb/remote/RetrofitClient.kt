package com.example.galeriaudb.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // URL base de la API de Picsum
    private const val BASE_URL = "https://picsum.photos/v2/"

    // Interceptor para mostrar logs de las peticiones en Logcat
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    // Cliente HTTP configurado con el logger
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()


    // Instancia única de Retrofit
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
