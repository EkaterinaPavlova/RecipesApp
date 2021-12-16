package com.hfad.recipesapp.ui.fragments.recipes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hfad.recipesapp.R
import com.hfad.recipesapp.adapters.RecipesAdapter
import com.hfad.recipesapp.databinding.FragmentRecipesBinding
import com.hfad.recipesapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes), RecipesAdapter.Listener {

    private val binding: FragmentRecipesBinding by viewBinding()
    private val viewModel by viewModels<MainViewModel>()
    private val adapter = RecipesAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.results)
        })
    }

    override fun clickItem(id: Int) {

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container_view,
                RecipeDetailsFragment.newInstance(id)
            )
            .addToBackStack(null)
            .commit()

    }
}