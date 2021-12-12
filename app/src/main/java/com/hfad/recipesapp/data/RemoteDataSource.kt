package com.hfad.recipesapp.data

import android.util.Log
import com.hfad.recipesapp.data.network.ApiResponse
import com.hfad.recipesapp.data.network.FoodRecipesApi
import com.hfad.recipesapp.models.FoodRecipe
import com.hfad.recipesapp.util.Constants.Companion.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    fun getRecipes(query: String): Flow<ApiResponse> = flow {
        try {
            val recepies = foodRecipesApi.getRecipes(API_KEY, 50, query)
            emit(recepies)
        } catch (exception: HttpException) {
            Log.d("ExceptionProblem", exception.toString())

        } catch (exception: IOException) {
            Log.d("ExceptionProblem", exception.toString())
        }
    }
}