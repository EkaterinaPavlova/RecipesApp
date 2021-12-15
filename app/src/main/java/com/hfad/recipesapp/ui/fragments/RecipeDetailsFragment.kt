package com.hfad.recipesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.hfad.recipesapp.R
import com.hfad.recipesapp.databinding.FragmentRecipeDetailsBinding
import com.hfad.recipesapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private val binding: FragmentRecipeDetailsBinding by viewBinding()
    private val viewModel by viewModels<MainViewModel>()

    companion object {

        private const val ID_SELECT_RECIPE = "ID_SELECT_RECIPE"

        fun newInstance(id: Int) : RecipeDetailsFragment {
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
            viewModel.getSelectRecipe(selectRecipeId).observe(viewLifecycleOwner, Observer {
                binding.titleRecipe.text = it.title
            })
        }
    }
}
