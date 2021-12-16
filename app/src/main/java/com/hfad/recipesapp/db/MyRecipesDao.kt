package com.hfad.recipesapp.db

import androidx.room.*
import com.hfad.recipesapp.models.FavoriteRecipe
import com.hfad.recipesapp.models.MyRecipe
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface MyRecipesDao {

    // My recipes
    // добавить рецепт в бд
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: MyRecipe)

    //удалить рецепт из бд
    @Query("DELETE FROM my_recipes_table WHERE id = :id")
    suspend fun deleteRecipe(id: Int)

    //получить список всех рецептов
    @Query("SELECT*FROM my_recipes_table")
    fun getAllRecipes() : Flow<List<MyRecipe>>

    // получить выбранный рецепт
    @Query("SELECT*FROM my_recipes_table WHERE id = :id")
    suspend fun getSelectRecipe(id: Int) : MyRecipe


    // Favorite recipes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteRecipe(recipe: FavoriteRecipe)

    @Query("DELETE FROM favorite_recipes_table WHERE id = :id")
    suspend fun deleteFavoriteRecipe(id: Int)

    @Query("SELECT*FROM favorite_recipes_table")
    fun getAllFavoriteRecipes() : Flow<List<FavoriteRecipe>>

    @Query("SELECT*FROM favorite_recipes_table WHERE id = :id")
    suspend fun getSelectFavoriteRecipe(id: Int) : FavoriteRecipe
}