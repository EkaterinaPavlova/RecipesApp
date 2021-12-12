package com.hfad.recipesapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "my_recipes_table")
data class MyRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    //val image: String,
    val title: String,

    val ingredients: String,
    //val ingredients: List<Ingredient>,

    val summary: String
) {

    data class Ingredient(
        val title: String,
        val count: String
    )
}
