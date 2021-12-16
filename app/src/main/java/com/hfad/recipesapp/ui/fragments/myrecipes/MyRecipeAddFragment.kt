package com.hfad.recipesapp.ui.fragments.myrecipes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hfad.recipesapp.App
import com.hfad.recipesapp.R
import com.hfad.recipesapp.databinding.FragmentMyRecipeAddBinding
import com.hfad.recipesapp.viewmodels.MyRecipesViewModel
import com.hfad.recipesapp.viewmodels.MyRecipesViewModelFactory
import com.hfad.recipesapp.models.MyRecipe

class MyRecipeAddFragment : Fragment(R.layout.fragment_my_recipe_add) {

    private val myRecipeViewModel: MyRecipesViewModel by viewModels {
        MyRecipesViewModelFactory((requireActivity().application as App).repository)
    }

    private val binding: FragmentMyRecipeAddBinding by viewBinding()

    companion object {
        fun newInstance(): MyRecipeAddFragment {
            return MyRecipeAddFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val range = 0..10000
        binding.saveRecipe.setOnClickListener {
            if(binding.titleRecipe.text.isNullOrEmpty() ||
                binding.ingredients.text.isNullOrEmpty() ||
                binding.summary.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Please, fill in form fields.", Toast.LENGTH_SHORT).show()

            } else {
                myRecipeViewModel.insert(
                    MyRecipe(
                        id = range.random(),
                        title = binding.titleRecipe.text.toString(),
                        ingredients = binding.ingredients.text.toString(),
                        summary = binding.summary.text.toString()
                    )
                )
                Toast.makeText(requireContext(), "New recipe created!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }

        }
    }
}