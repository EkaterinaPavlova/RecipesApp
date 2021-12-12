package com.hfad.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfad.recipesapp.databinding.ItemMyRecipeBinding
import com.hfad.recipesapp.models.MyRecipe

class MyRecipesAdapter(
    private val listener: Listener

) : ListAdapter<MyRecipe, MyRecipesAdapter.MyRecipesViewHolder>(MyRecipesComparator()) {

    interface Listener {
        fun clickItem(id: Int)
        fun deleteItem(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecipesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyRecipeBinding.inflate(inflater, parent, false)
        return MyRecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyRecipesViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            titleRecipe.text = item.title
        }

        holder.binding.cardMyRecipe.setOnClickListener {
            listener.clickItem(item.id)
        }

        holder.binding.buttonDelete.setOnClickListener {
            listener.deleteItem(item.id)
        }
    }

    class MyRecipesViewHolder(val binding: ItemMyRecipeBinding) :
        RecyclerView.ViewHolder(binding.root)
}

class MyRecipesComparator : DiffUtil.ItemCallback<MyRecipe>() {
    override fun areItemsTheSame(oldItem: MyRecipe, newItem: MyRecipe): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: MyRecipe, newItem: MyRecipe): Boolean {
        return oldItem.id == newItem.id
    }
}