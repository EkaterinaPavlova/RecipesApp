package com.hfad.recipesapp.ui.fragments.recipes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.hfad.recipesapp.models.FavoriteRecipe
import com.hfad.recipesapp.util.NetworkResult
import com.hfad.recipesapp.viewmodels.MainViewModel
import com.hfad.recipesapp.viewmodels.MyRecipesViewModel
import com.hfad.recipesapp.viewmodels.MyRecipesViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private val binding: FragmentRecipeDetailsBinding by viewBinding()
    private val viewModel by viewModels<MainViewModel>()

    private val myRecipeViewModel: MyRecipesViewModel by viewModels {
        MyRecipesViewModelFactory((requireActivity().application as App).repository)
    }

    private var favoriteRecipe: FavoriteRecipe? = null
    private var recipeInDb: Boolean = false

    companion object {
        private const val ID_SELECT_RECIPE = "ID_SELECT_RECIPE"
        fun newInstance(id: Int): RecipeDetailsFragment {
            val fragment = RecipeDetailsFragment()
            fragment.arguments = bundleOf(ID_SELECT_RECIPE to id)
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectRecipeId: Int?
        arguments.let {
            selectRecipeId = arguments?.getInt(ID_SELECT_RECIPE)
        }

        if (selectRecipeId != null) {
            viewModel.getSelectRecipe(selectRecipeId)
        }

        if (selectRecipeId != null) {
            initObserve(selectRecipeId)
        }
        initListener()
    }

    private fun initListener() {
        binding.favorite.setOnClickListener {
            if (favoriteRecipe != null) {
                if (recipeInDb) {
                    Toast.makeText(
                        requireContext(),
                        "The recipe is already in favorite recipes!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    myRecipeViewModel.insertFavoriteRecipe(favoriteRecipe!!)
                    binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
                    Toast.makeText(
                        requireContext(),
                        "Recipe added to favorite recipes!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun initObserve(selectRecipeId: Int) {
        viewModel.selectRecipe.observe(viewLifecycleOwner, Observer { result ->

            when(result) {
                is NetworkResult.Success -> {
                    val recipe = result.data
                    val listIngredients = recipe.extendedIngredients.map { ingredient ->
                        ingredient.original
                    }

                    binding.titleRecipe.text = recipe.title
                    binding.summary.text = recipe.summary
                    binding.nameIngredients.text = listIngredients.toString() // поменять

                    Glide.with(this)
                        .load(recipe.image)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(binding.image)

                    favoriteRecipe = FavoriteRecipe(
                        id = recipe.id,
                        title = recipe.title,
                        image = recipe.image,
                        summary = recipe.summary,
                        extendedIngredients = listIngredients.toString()
                    )

                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        })


        myRecipeViewModel.allFavoriteRecipes.observe(viewLifecycleOwner, Observer { list ->
            list.map { it ->
                if (it.id == selectRecipeId) {
                    binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
                    recipeInDb = true
                }
            }

        })
    }
}
