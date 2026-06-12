package com.louis.nit3213finalapp.data.repository

import com.louis.nit3213finalapp.data.api.ApiService
import com.louis.nit3213finalapp.data.model.DashboardResponse
import com.louis.nit3213finalapp.data.model.LoginRequest
import com.louis.nit3213finalapp.data.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(username, password))
    }

    suspend fun getDashboard(): Response<DashboardResponse> {
        return apiService.getDashboard()
    }
}
