package com.example.galeriaudb.remote

import com.example.galeriaudb.model.Foto
import retrofit2.http.GET

interface ApiService {
    @GET("list?page=1&limit=50")
    suspend fun getFotos(): List<Foto>
}
