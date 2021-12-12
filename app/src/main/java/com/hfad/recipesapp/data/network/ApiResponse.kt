package com.hfad.recipesapp.data.network

data class ApiResponse(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
) {
    data class Result(
        val id: Int,
        val image: String,
        val imageType: String,
        val title: String
    )
}