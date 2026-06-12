package com.louis.nit3213finalapp.data.api

import com.louis.nit3213finalapp.data.model.DashboardResponse
import com.louis.nit3213finalapp.data.model.LoginRequest
import com.louis.nit3213finalapp.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("sydney/auth")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("dashboard/books")
    suspend fun getDashboard(): Response<DashboardResponse>
}
