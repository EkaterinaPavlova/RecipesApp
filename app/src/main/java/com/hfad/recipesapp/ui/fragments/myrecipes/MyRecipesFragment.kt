package com.hfad.recipesapp.ui.fragments.myrecipes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hfad.recipesapp.App
import com.hfad.recipesapp.R
import com.hfad.recipesapp.adapters.MyRecipesAdapter
import com.hfad.recipesapp.databinding.FragmentMyRecipesBinding
import com.hfad.recipesapp.models.MyRecipe
import com.hfad.recipesapp.viewmodels.MyRecipesViewModel
import com.hfad.recipesapp.viewmodels.MyRecipesViewModelFactory

class MyRecipesFragment : Fragment(R.layout.fragment_my_recipes), MyRecipesAdapter.Listener {

    private val binding: FragmentMyRecipesBinding by viewBinding()
    private val adapter = MyRecipesAdapter(this)
    private var listRecipes: MutableList<MyRecipe> = mutableListOf()
    private var listSearch: MutableList<MyRecipe> = mutableListOf()

    private val myRecipeViewModel: MyRecipesViewModel by viewModels {
        MyRecipesViewModelFactory((requireActivity().application as App).repository)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // поиск рецепта по названию
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listSearch = mutableListOf()

                for (i in 0 until listRecipes.size) {
                    if (listRecipes[i].title.lowercase()
                            .contains(newText?.lowercase().toString())
                    ) {
                        listSearch.add(listRecipes[i])
                    }
                }
                adapter.submitList(listSearch)
                return true
            }
        })

        // получаем из бд список рецептов
        myRecipeViewModel.allRecipes.observe(viewLifecycleOwner, Observer { list ->
            listRecipes = mutableListOf()
            list.map {
                listRecipes.add(it)
            }
            if(listRecipes.isNullOrEmpty()){
                binding.recyclerView.visibility = View.GONE
                binding.notRecipes.visibility = View.VISIBLE
            }else {
                binding.notRecipes.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(listRecipes)
            }
        })


        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter

            // запускаем фрагмент для добавления нового рецепта в бд
            add.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container_view,
                        MyRecipeAddFragment.newInstance()
                    )
                    .addToBackStack(null)
                    .commit()

            }
        }
    }

    override fun clickItem(id: Int) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, MyRecipeDetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun deleteItem(id: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Delete this recipe?")
        builder.setPositiveButton("YES") { _: DialogInterface, _: Int ->
            myRecipeViewModel.delete(id)
        }
        builder.setNegativeButton("CANCEL") { _: DialogInterface, _: Int -> }
        builder.show()

    }
}