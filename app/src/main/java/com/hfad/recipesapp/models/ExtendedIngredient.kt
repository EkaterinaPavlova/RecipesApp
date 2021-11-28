package com.hfad.recipesapp.models


import com.google.gson.annotations.SerializedName

data class ExtendedIngredient(
        @SerializedName("amount")
        val amount: Double,
        @SerializedName("consitency")
        val consitency: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("unit")
        val unit: String
    )