package com.hfad.recipesapp.db

import android.util.Log
import androidx.annotation.WorkerThread
import com.hfad.recipesapp.db.MyRecipesDao
import com.hfad.recipesapp.models.FavoriteRecipe
import com.hfad.recipesapp.models.MyRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyRecipesRepository(
    private val myRecipesDao: MyRecipesDao
) {

    // My recipes
    // получаем данные из бд
    val allRecipes: Flow<List<MyRecipe>> = myRecipesDao.getAllRecipes()

    fun getSelectRecipe(id: Int) : Flow<MyRecipe> = flow{
        emit(myRecipesDao.getSelectRecipe(id))
    }

    @WorkerThread
    suspend fun delete(id: Int){
        myRecipesDao.deleteRecipe(id)
    }

    @WorkerThread
    suspend fun insert(recipe: MyRecipe) {
        myRecipesDao.insert(recipe)
    }



    // Favorite recipes
    // получаем данные из бд
    val allFavoriteRecipes: Flow<List<FavoriteRecipe>> = myRecipesDao.getAllFavoriteRecipes()

    fun getSelectFavoriteRecipe(id: Int) : Flow<FavoriteRecipe> = flow{
        emit(myRecipesDao.getSelectFavoriteRecipe(id))
    }

    @WorkerThread
    suspend fun deleteFavoriteRecipe(id: Int){
        myRecipesDao.deleteFavoriteRecipe(id)
    }

    @WorkerThread
    suspend fun insertFavoriteRecipe(recipe: FavoriteRecipe) {
        myRecipesDao.insertFavoriteRecipe(recipe)
    }
}