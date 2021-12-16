package com.hfad.recipesapp.ui.fragments.recipes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hfad.recipesapp.R
import com.hfad.recipesapp.adapters.RecipesAdapter
import com.hfad.recipesapp.databinding.FragmentRecipesBinding
import com.hfad.recipesapp.util.NetworkResult
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

        initObserve()
        initSearch()

    }

    private fun initObserve() {
        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            when(it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data.results)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        })
    }

    private fun initSearch() {
        val search = binding.searchView

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)  {
                    viewModel.searchRecipes(query)
                    search.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)  {
                    viewModel.searchRecipes(newText)
                }
                return true
            }
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