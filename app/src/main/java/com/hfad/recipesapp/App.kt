package com.hfad.recipesapp

import android.app.Application
import com.hfad.recipesapp.db.MyRecipesRepository
import com.hfad.recipesapp.db.MyRecipesRoomDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class App : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { MyRecipesRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MyRecipesRepository(database.myRecipesDao()) }
}