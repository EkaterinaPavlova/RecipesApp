package com.hfad.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.hfad.recipesapp.databinding.ActivityMainBinding
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.hfad.recipesapp.ui.fragments.FavoriteRecipesFragment
import com.hfad.recipesapp.ui.fragments.MyRecipesFragment
import com.hfad.recipesapp.ui.fragments.RecipesFragment
import androidx.appcompat.app.ActionBarDrawerToggle
import com.hfad.recipesapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    private val recipesFragment = RecipesFragment()
    private val favoriteRecipesFragment = FavoriteRecipesFragment()
    private val myRecipesFragment = MyRecipesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            title = "Recipes"
            replaceFragment(recipesFragment)
        }

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.apply {
            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_recipes -> {
                        title = "Recipes"
                        replaceFragment(recipesFragment)
                    }
                    R.id.nav_fav_recipes -> {
                        title = "Favorite recipes"
                        replaceFragment(favoriteRecipesFragment)
                    }
                    R.id.nav_my_recipes -> {
                        title = "My recipes"
                        replaceFragment(myRecipesFragment)
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            //.addToBackStack(null)
            .commit()
    }
}