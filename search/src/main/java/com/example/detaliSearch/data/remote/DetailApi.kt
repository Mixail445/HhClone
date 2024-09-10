package com.example.detaliSearch.data.remote

import com.example.detaliSearch.data.model.DetailResponseDto
import retrofit2.http.GET

interface DetailApi {
    @GET("vacancies")
    suspend fun getVacancies(): DetailResponseDto
}
