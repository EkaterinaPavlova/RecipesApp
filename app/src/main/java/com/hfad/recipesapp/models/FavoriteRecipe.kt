package com.hfad.recipesapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes_table")
data class FavoriteRecipe(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val extendedIngredients: String,
)
