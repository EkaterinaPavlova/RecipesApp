package com.hfad.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hfad.recipesapp.R
import com.hfad.recipesapp.models.RecipesResponse
import com.hfad.recipesapp.databinding.ItemRecipeBinding

class RecipesAdapter(
    private val listener: Listener
) :
    ListAdapter<RecipesResponse.Result, RecipesAdapter.ViewHolder>(Comparator()) {

    interface Listener {
        fun clickItem(id: Int)
    }

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

        holder.itemView.setOnClickListener {
            listener.clickItem(currentItem.id)
        }
    }

    class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipesResponse.Result) {
            binding.apply {
                nameRecipe.text = recipe.title

                Glide.with(itemView.context)
                    .load(recipe.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(imageRecipe)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<RecipesResponse.Result>() {
        override fun areItemsTheSame(oldItem: RecipesResponse.Result, newItem: RecipesResponse.Result) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipesResponse.Result, newItem: RecipesResponse.Result) =
            oldItem == newItem
    }
}