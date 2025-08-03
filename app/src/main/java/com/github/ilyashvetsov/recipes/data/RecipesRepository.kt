package com.github.ilyashvetsov.recipes.data

import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


private const val BASE_URL = "https://recipes.androidsprint.ru/api/"

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
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    fun getRecipeById(recipeId: Int, callback: (Recipe?) -> Unit) {
        threadPool.submit {
            val call = recipesApiService.getRecipeById(recipeId)
            val response = call.execute()
            if (response.isSuccessful) {
                val recipe = response.body()
                callback(recipe)
            }
        }
    }

    fun getRecipesListByIds(ids: String, callback: (List<Recipe>?) -> Unit) {
        threadPool.submit {
            val call = recipesApiService.getRecipesListByIds(ids)
            val response = call.execute()
            if (response.isSuccessful) {
                val recipeList = response.body()
                callback(recipeList)
            }
        }
    }

    fun getCategoryById(categoryId: Int): Category? {
        val call = recipesApiService.getCategoryById(categoryId)
        val response = call.execute()
        return if (response.isSuccessful) {
            val category = response.body()
            category
        } else {
            null
        }
    }

    fun getRecipesListByCategoryId(categoryId: Int, callback: (List<Recipe>?) -> Unit) {
        threadPool.submit {
            val call = recipesApiService.getRecipesListByCategoryId(categoryId)
            val response = call.execute()
            if (response.isSuccessful) {
                val recipesList = response.body()
                callback(recipesList)
            }
        }
    }

    fun getCategories(callback: (List<Category>?) -> Unit) {
        threadPool.submit {
            val call = recipesApiService.getCategories()
            val response = call.execute()
            if (response.isSuccessful) {
                val categories = response.body()
                callback(categories)
            }
        }
    }
}

