package com.hfad.recipesapp.viewmodels

import androidx.lifecycle.*
import com.hfad.recipesapp.db.MyRecipesRepository
import com.hfad.recipesapp.models.FavoriteRecipe
import com.hfad.recipesapp.models.MyRecipe
import kotlinx.coroutines.launch

class MyRecipesViewModel(private val repository: MyRecipesRepository): ViewModel() {

    // My recipes
    val allRecipes: LiveData<List<MyRecipe>> = repository.allRecipes.asLiveData()

    fun getSelectRecipe(id: Int): LiveData<MyRecipe> = repository.getSelectRecipe(id).asLiveData()

    fun insert(recipe: MyRecipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }


    // Favorite recipes
    val allFavoriteRecipes: LiveData<List<FavoriteRecipe>> = repository.allFavoriteRecipes.asLiveData()

    fun getSelectFavoriteRecipe(id: Int): LiveData<FavoriteRecipe> = repository.getSelectFavoriteRecipe(id).asLiveData()

    fun insertFavoriteRecipe(recipe: FavoriteRecipe) = viewModelScope.launch {
        repository.insertFavoriteRecipe(recipe)
    }

    fun deleteFavoriteRecipe(id: Int) = viewModelScope.launch {
        repository.deleteFavoriteRecipe(id)
    }

}


class MyRecipesViewModelFactory(private val repository: MyRecipesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyRecipesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyRecipesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}