package com.hfad.recipesapp.ui.fragments.myrecipes

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hfad.recipesapp.App
import com.hfad.recipesapp.R
import com.hfad.recipesapp.databinding.FragmentMyRecipeDetailsBinding
import com.hfad.recipesapp.viewmodels.MyRecipesViewModel
import com.hfad.recipesapp.viewmodels.MyRecipesViewModelFactory

class MyRecipeDetailsFragment : Fragment(R.layout.fragment_my_recipe_details) {

    private val myRecipeViewModel: MyRecipesViewModel by viewModels {
        MyRecipesViewModelFactory((requireActivity().application as App).repository)
    }

    private val binding : FragmentMyRecipeDetailsBinding by viewBinding()

    companion object{
        private const val ID_RECIPE = "ID_RECIPE"

        fun newInstance(id: Int) : MyRecipeDetailsFragment {
            val fragment = MyRecipeDetailsFragment()
            fragment.arguments = bundleOf(ID_RECIPE to id)
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recipeId : Int?
        arguments.let {
            recipeId = arguments?.getInt(ID_RECIPE)
        }

        if (recipeId != null) {
            myRecipeViewModel.getSelectRecipe(id = recipeId).observe(viewLifecycleOwner, Observer {
                binding.apply {
                    titleRecipe.text = it.title
                    ingredients.text = it.ingredients
                    summary.text = it.summary
                }
            })
        }




    }
}