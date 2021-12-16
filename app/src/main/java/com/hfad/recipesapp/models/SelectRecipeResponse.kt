package com.hfad.recipesapp.models

data class SelectRecipeResponse(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val extendedIngredients: List<ExtendedIngredient>,

    ) {
    data class ExtendedIngredient(
        val id: Int,
        val image: String,
        val original: String, // название игредиента уже с количеством
        val name: String,
        val amount: Double,
    )
}