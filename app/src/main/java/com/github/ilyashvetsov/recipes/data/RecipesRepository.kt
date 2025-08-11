package com.github.ilyashvetsov.recipes.data

import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RecipesRepository {
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

    suspend fun getRecipeById(recipeId: Int): Recipe? {
        try {
            val recipe = recipesApiService.getRecipeById(recipeId)
            return recipe
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getRecipesListByIds(ids: String): List<Recipe>? {
        try {
            val recipeList = recipesApiService.getRecipesListByIds(ids)
            return recipeList
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getRecipesListByCategoryId(categoryId: Int): List<Recipe>? {
        try {
            val recipesList = recipesApiService.getRecipesListByCategoryId(categoryId)
            return recipesList
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getCategories(): List<Category>? {
        try {
            val listCategory = recipesApiService.getCategories()
            return listCategory
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}

