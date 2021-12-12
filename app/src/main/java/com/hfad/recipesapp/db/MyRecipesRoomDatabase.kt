package com.hfad.recipesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hfad.recipesapp.models.MyRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [MyRecipe::class], version = 1, exportSchema = false)
abstract class MyRecipesRoomDatabase : RoomDatabase() {

    abstract fun myRecipesDao(): MyRecipesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: MyRecipesRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MyRecipesRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRecipesRoomDatabase::class.java,
                    "items_database"
                )
                    //.addCallback(MyRecipesDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class MyRecipesDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {

                    val dao = database.myRecipesDao()

                    // added examples
                    val recipe1 = MyRecipe(
                        id = 0,
                        title = "A title",
                        ingredients = "Some ingredients",
                        summary = "Some summary")
                    dao.insert(recipe1)

                }
            }
        }
    }
}