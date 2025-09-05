package com.github.ilyashvetsov.recipes.di

import android.content.Context
import androidx.room.Room
import com.github.ilyashvetsov.recipes.data.CategoriesDao
import com.github.ilyashvetsov.recipes.data.RecipeApiService
import com.github.ilyashvetsov.recipes.data.RecipeDao
import com.github.ilyashvetsov.recipes.data.RecipeDataBase
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    private val recipeDataBase: RecipeDataBase = Room.databaseBuilder(
        context,
        RecipeDataBase::class.java,
        "app_database"
    ).build()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val recipeDao: RecipeDao = recipeDataBase.recipeDao()
    private val categoriesDao: CategoriesDao = recipeDataBase.categoriesDao()

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val recipesApiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    private val repository = RecipesRepository(
        recipeDao = recipeDao,
        categoriesDao = categoriesDao,
        recipesApiService = recipesApiService,
        ioDispatcher = ioDispatcher
    )
    val categoriesListViewModelFactory = CategoriesListViewModelFactory(repository)
    val favoritesListViewModelFactory = FavoritesViewModelFactory(repository)
    val recipesListViewModelFactory = RecipesListViewModelFactory(repository)
    val recipeViewModelFactory = RecipeViewModelFactory(repository)
}