package com.hfad.recipesapp.ui.fragments.favoriterecipes

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hfad.recipesapp.App
import com.hfad.recipesapp.R
import com.hfad.recipesapp.databinding.FragmentRecipeDetailsBinding
import com.hfad.recipesapp.viewmodels.MyRecipesViewModel
import com.hfad.recipesapp.viewmodels.MyRecipesViewModelFactory

class FavoriteRecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private val binding: FragmentRecipeDetailsBinding by viewBinding()

    private val myRecipeViewModel: MyRecipesViewModel by viewModels {
        MyRecipesViewModelFactory((requireActivity().application as App).repository)
    }

    companion object {

        private const val ID_SELECT_RECIPE = "ID_SELECT_RECIPE"

        fun newInstance(id: Int) : FavoriteRecipeDetailsFragment {
            val fragment = FavoriteRecipeDetailsFragment()
            fragment.arguments = bundleOf(ID_SELECT_RECIPE to id)
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId : Int?
        arguments.let {
            recipeId = arguments?.getInt(ID_SELECT_RECIPE)
        }

        if (recipeId != null) {
            myRecipeViewModel.getSelectFavoriteRecipe(id = recipeId).observe(viewLifecycleOwner, Observer {
                binding.apply {
                    textAddToFavorite.visibility = View.GONE
                    favorite.visibility = View.GONE

                    Glide.with(this@FavoriteRecipeDetailsFragment)
                        .load(it.image)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(binding.image)

                    titleRecipe.text = it.title
                    summary.text = it.summary
                    nameIngredients.text = it.extendedIngredients
                }
            })
        }
    }
}