package com.myjar.jarassignment.data.api

import com.myjar.jarassignment.data.model.ComputerItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/objects")
    suspend fun fetchResults(): List<ComputerItem>

    @GET("/objects/{id}")
    suspend fun fetchResultById(@Path("id") id: String): ComputerItem

}