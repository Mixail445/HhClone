package com.example.search.domain.model

data class SearchResponse(
    val offers: List<Offers>,
    val vacancies: List<Vacancy>,
)
