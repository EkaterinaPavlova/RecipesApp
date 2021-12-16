package com.hfad.recipesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hfad.recipesapp.data.Repository
import com.hfad.recipesapp.models.RecipesResponse
import com.hfad.recipesapp.models.SelectRecipeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _recipes = MutableLiveData<RecipesResponse>()
    val recipes: LiveData<RecipesResponse> = _recipes

    private val _selectRecipe = MutableLiveData<SelectRecipeResponse>()
    val selectRecipe: LiveData<SelectRecipeResponse> = _selectRecipe

    init {
        viewModelScope.launch {
            repository.remote.getRecipes("pasta").collect {
                _recipes.value = it
            }
        }
    }

    fun getSelectRecipe(id: Int) : LiveData<SelectRecipeResponse> {
         viewModelScope.launch {
            repository.remote.getSelectRecipe(id).collect {
                _selectRecipe.value = it
            }
        }
        return selectRecipe
    }
}