# **Recipes App**

Приложение - сборник рецептов.

Технологии: MVVM, DaggerHilt, Room, Retrofit, Gson, Coroutines, Flow, LiveData, Glide. 
Для получения данных используется api https://api.spoonacular.com/

## О приложении

**Главный экран**

По умолчанию на главном экране выводится список рецептов по запросу: "pizza". 
В поисковой строке вводится нужный запрос. 
При нажатии на карточку рецепта открывается экран деталей.

![alt-текст](https://github.com/EkaterinaPavlova/RecipesApp/blob/master/app/src/main/res/drawable/main_layout_with_drawerlayout.png "")
![alt-текст](https://github.com/EkaterinaPavlova/RecipesApp/blob/master/app/src/main/res/drawable/main_layout.png "")


**Детали рецепта**

Экран предоставляет информацию о рецепте. Рецепт можно добавить в список "Favorite recipes", который сохраняется в базу данных.

![alt-текст](https://github.com/EkaterinaPavlova/RecipesApp/blob/master/app/src/main/res/drawable/details_of_recipe.png "")


**Экран "Favorite recipe"**

На экране выводится список понравившихся рецептов. При нажатии на карточку - открываются детали рецепта. 
При долгом нажатии появляется Alert Dialog - удаление рецепта из списка базы данных.

![alt-текст](https://github.com/EkaterinaPavlova/RecipesApp/blob/master/app/src/main/res/drawable/favorite_recipes.png "")
![alt-текст](https://github.com/EkaterinaPavlova/RecipesApp/blob/master/app/src/main/res/drawable/alert_dialog.png "")



**Экран "My recipes"**

На экране выводится список собственных рецептов. Есть поиск по названию рецепта и возможность удаления рецепта из списка.
При нажатии на карточку рецепта откроется экране деталей рецепта.
При нажатии на кнопку "+" открывается экран, на котором необходимо внести данные о рецепте и сохранить его в базу данных.

![alt-текст](https://github.com/EkaterinaPavlova/RecipesApp/blob/master/app/src/main/res/drawable/my_recipes.png "")



## Примеры кода

**Пример запросов с базой данных**
```kotlin
@Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: MyRecipe)
    
@Query("SELECT*FROM my_recipes_table WHERE id = :id")
    suspend fun getSelectRecipe(id: Int) : MyRecipe
```

**Пример запроса рецептов по API**
```kotlin
@GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
        @Query("query") query: String
    ) : RecipesResponse
```

**Пример кода из MainViewModel (получение - передача запроса пользователя)**
```kotlin
companion object {
        private const val DEFAULT_QUERY = "pizza"
        private const val SEARCH_DELAY_MILLIS = 700L
        private const val MIN_QUERY_LENGTH = 2
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    private val _recipes = currentQuery.switchMap {
        repository.remote.getRecipes(it).asLiveData()
    }

    val recipes: LiveData<NetworkResult<RecipesResponse>> = _recipes

    fun searchRecipes(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY_MILLIS)
            if (query.length > MIN_QUERY_LENGTH) {
                currentQuery.value = query
            }
        }
    }
```


**Пример кода из RecipesFragment (главный экран с рецептами)**
```kotlin
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
```



