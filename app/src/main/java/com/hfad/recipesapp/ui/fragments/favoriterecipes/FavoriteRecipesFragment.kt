package com.hfad.recipesapp.ui.fragments.favoriterecipes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hfad.recipesapp.App
import com.hfad.recipesapp.R
import com.hfad.recipesapp.adapters.FavoriteRecipesAdapter
import com.hfad.recipesapp.adapters.RecipesAdapter
import com.hfad.recipesapp.databinding.FragmentFavoriteRecipesBinding
import com.hfad.recipesapp.databinding.FragmentRecipeDetailsBinding
import com.hfad.recipesapp.models.FavoriteRecipe
import com.hfad.recipesapp.models.MyRecipe
import com.hfad.recipesapp.ui.fragments.myrecipes.MyRecipeAddFragment
import com.hfad.recipesapp.ui.fragments.recipes.RecipeDetailsFragment
import com.hfad.recipesapp.viewmodels.MyRecipesViewModel
import com.hfad.recipesapp.viewmodels.MyRecipesViewModelFactory

class FavoriteRecipesFragment : Fragment(R.layout.fragment_favorite_recipes), FavoriteRecipesAdapter.Listener {

    private val binding: FragmentFavoriteRecipesBinding by viewBinding()
    private val adapter = FavoriteRecipesAdapter(this)

    private val myRecipeViewModel: MyRecipesViewModel by viewModels {
        MyRecipesViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserve()

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
    }

    private fun initObserve(){
        myRecipeViewModel.allFavoriteRecipes.observe(viewLifecycleOwner, Observer { list ->

            if(list.isNullOrEmpty()){
                binding.recyclerView.visibility = View.GONE
                binding.notRecipes.visibility = View.VISIBLE
            }else {
                binding.notRecipes.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(list)
            }
        })
    }

    override fun clickItem(id: Int) {
        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container_view,
                FavoriteRecipeDetailsFragment.newInstance(id)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun deleteItem(id: Int) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Delete this recipe?")
        builder.setPositiveButton("YES") { _: DialogInterface, _: Int ->
            myRecipeViewModel.deleteFavoriteRecipe(id)
        }
        builder.setNegativeButton("CANCEL") { _: DialogInterface, _: Int -> }
        builder.show()
    }
}