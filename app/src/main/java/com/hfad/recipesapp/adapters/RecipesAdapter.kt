package com.hfad.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.recipesapp.data.network.ApiResponse
import com.hfad.recipesapp.databinding.ItemRecipeBinding

class RecipesAdapter() :
    ListAdapter<ApiResponse.Result, RecipesAdapter.ViewHolder>(
        Comparator()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: ApiResponse.Result) {
            binding.apply {
                nameRecipe.text = recipe.title

                Glide.with(itemView.context)
                    .load(recipe.image)
                    .into(imageRecipe)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<ApiResponse.Result>() {
        override fun areItemsTheSame(oldItem: ApiResponse.Result, newItem: ApiResponse.Result) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ApiResponse.Result, newItem: ApiResponse.Result) =
            oldItem == newItem
    }
}