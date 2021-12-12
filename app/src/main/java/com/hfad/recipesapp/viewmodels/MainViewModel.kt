package com.hfad.recipesapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hfad.recipesapp.data.Repository
import com.hfad.recipesapp.data.network.ApiResponse
import com.hfad.recipesapp.models.FoodRecipe
import com.hfad.recipesapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _recipes = MutableLiveData<ApiResponse>()
    val recipes: LiveData<ApiResponse> = _recipes

    init {
        viewModelScope.launch {
            repository.remote.getRecipes("pasta").collect {
                _recipes.value = it
            }
        }

    }
}