package com.github.ilyashvetsov.recipes.data

import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecipesRepository(
    private val recipeDatabase: RecipeDataBase
) {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private var retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val recipesApiService = retrofit.create(RecipeApiService::class.java)
    private val recipeDao: RecipeDao = recipeDatabase.recipeDao()

    suspend fun getCategoriesFromCache(): List<Category> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllCategories()
        }
    }

    suspend fun insertCategories(categories: List<Category>) {
        withContext(Dispatchers.IO) {
            recipeDao.insertCategories(categories = categories)
        }
    }

    suspend fun getRecipeById(recipeId: Int): Recipe? {
        return withContext(Dispatchers.IO) {
            try {
                val recipe = recipesApiService.getRecipeById(recipeId)
                recipe
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getRecipesListByIds(ids: String): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                val recipeList = recipesApiService.getRecipesListByIds(ids)
                recipeList
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getRecipesListByCategoryId(categoryId: Int): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                val recipesList = recipesApiService.getRecipesListByCategoryId(categoryId)
                recipesList
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getCategories(): List<Category>? {
        return withContext(Dispatchers.IO) {
            try {
                val listCategory = recipesApiService.getCategories()
                listCategory
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

