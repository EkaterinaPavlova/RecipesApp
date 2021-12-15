package com.hfad.recipesapp.data.network

import com.hfad.recipesapp.models.RecipesResponse
import com.hfad.recipesapp.models.SelectRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodRecipesApi {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
        @Query("query") query: String
    ) : RecipesResponse

    @GET("recipes/{id}/information?includeNutrition=false")
    suspend fun getSelectRecipe(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ) : SelectRecipeResponse
}