package com.github.ilyashvetsov.recipes.data

import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipe/{id}")
    suspend fun getRecipeById(@Path("id") recipeId: Int): Recipe

    @GET("recipes")
    suspend fun getRecipesListByIds(@Query("ids") ids: String): List<Recipe>

    @GET("category/{id}/recipes")
    suspend fun getRecipesListByCategoryId(@Path("id") categoryId: Int): List<Recipe>

    @GET("category")
    suspend fun getCategories(): List<Category>
}