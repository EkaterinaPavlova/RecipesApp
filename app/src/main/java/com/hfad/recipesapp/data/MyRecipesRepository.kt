package com.hfad.recipesapp.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.hfad.recipesapp.db.MyRecipesDao
import com.hfad.recipesapp.models.MyRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyRecipesRepository(
    private val myRecipesDao: MyRecipesDao
) {

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
}