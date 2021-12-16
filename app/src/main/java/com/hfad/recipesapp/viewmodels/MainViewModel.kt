package com.hfad.recipesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.hfad.recipesapp.data.Repository
import com.hfad.recipesapp.models.RecipesResponse
import com.hfad.recipesapp.models.SelectRecipeResponse
import com.hfad.recipesapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private var searchJob: Job? = null

    private val _selectRecipe = MutableLiveData<NetworkResult<SelectRecipeResponse>>()
    val selectRecipe: LiveData<NetworkResult<SelectRecipeResponse>> = _selectRecipe

    companion object {
        private const val DEFAULT_QUERY = "pizza"
        private const val SEARCH_DELAY_MILLIS = 700L
        private const val MIN_QUERY_LENGTH = 2
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    private val _recipes = currentQuery.switchMap {
        repository.remote.getRecipes(it).asLiveData()
    }

    val recipes: LiveData<NetworkResult<RecipesResponse>> = _recipes

    fun searchRecipes(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY_MILLIS)
            if (query.length > MIN_QUERY_LENGTH) {
                currentQuery.value = query
            }
        }
    }

    fun getSelectRecipe(id: Int) {
        viewModelScope.launch {
            repository.remote.getSelectRecipe(id).collect {
                _selectRecipe.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}