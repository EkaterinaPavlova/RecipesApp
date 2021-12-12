package com.hfad.recipesapp.data.network

import com.hfad.recipesapp.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
        @Query("query") query: String
    ) : ApiResponse
}