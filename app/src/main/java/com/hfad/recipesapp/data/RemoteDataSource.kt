package com.hfad.recipesapp.data

import android.util.Log
import com.hfad.recipesapp.models.RecipesResponse
import com.hfad.recipesapp.models.SelectRecipeResponse
import com.hfad.recipesapp.data.network.FoodRecipesApi
import com.hfad.recipesapp.util.Constants.Companion.API_KEY
import com.hfad.recipesapp.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    fun getRecipes(query: String): Flow<NetworkResult<RecipesResponse>> = flow {
        try {
            val recepies = foodRecipesApi.getRecipes(API_KEY, 50, query)
            emit(NetworkResult.Success(recepies))
        } catch (exception: HttpException) {
            Log.d("ExceptionProblem", exception.toString())
            emit(NetworkResult.Error(exception.toString()))

        } catch (exception: IOException) {
            Log.d("ExceptionProblem", exception.toString())
            emit(NetworkResult.Error(exception.toString()))
        }
    }

    fun getSelectRecipe(id: Int): Flow<NetworkResult<SelectRecipeResponse>> = flow {
        try {
            val selectRecipe = foodRecipesApi.getSelectRecipe(id, API_KEY)
            emit(NetworkResult.Success(selectRecipe))
        } catch (exception: HttpException) {
            Log.d("ExceptionProblem", exception.toString())
            emit(NetworkResult.Error(exception.toString()))

        } catch (exception: IOException) {
            Log.d("ExceptionProblem", exception.toString())
            emit(NetworkResult.Error(exception.toString()))
        }
    }
}