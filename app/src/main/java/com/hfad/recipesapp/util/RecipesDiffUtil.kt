package com.hfad.recipesapp.util

import androidx.recyclerview.widget.DiffUtil
import com.hfad.recipesapp.models.FoodRecipe
import com.hfad.recipesapp.models.Result

class RecipesDiffUtil(
    private val oldList: List<Result>,
    private val newList: List<Result>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition] === newList[newItemPosition]
}