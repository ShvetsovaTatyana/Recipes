package com.github.ilyashvetsov.recipes.data

import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors




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
            try {
                val call = recipesApiService.getRecipeById(recipeId)
                val response = call.execute()
                if (response.isSuccessful) {
                    val recipe = response.body()
                    callback(recipe)
                } else
                    response.code()
            } catch (e: IOException) {
                e.printStackTrace()
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

