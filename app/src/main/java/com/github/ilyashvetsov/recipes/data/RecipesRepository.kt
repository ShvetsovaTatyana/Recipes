package com.github.ilyashvetsov.recipes.data

import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RecipesRepository(
    private val categoriesDao:CategoriesDao,
    private val recipeDao: RecipeDao,
    private val recipesApiService: RecipeApiService,
    private val ioDispatcher:CoroutineDispatcher
) {

    suspend fun getCategoriesFromCache(): List<Category> {
        return withContext(ioDispatcher) {
            categoriesDao.getAllCategories()
        }
    }

    suspend fun insertCategories(categories: List<Category>) {
        withContext(ioDispatcher) {
            categoriesDao.insertCategories(categories = categories)
        }
    }

    suspend fun getRecipesFromCache(categoryId: Int): List<Recipe> {
        return withContext(ioDispatcher) { recipeDao.getAllRecipes(categoryId = categoryId) }
    }

    suspend fun insertRecipes(recipes: List<Recipe>) {
        withContext(ioDispatcher) {
            recipeDao.insertRecipes(recipe = recipes)
        }
    }

    suspend fun getFavoritesFromCache(): List<Recipe> {
        return withContext(ioDispatcher) {
            recipeDao.getFavorites()
        }
    }

    suspend fun insertFavorites(recipe: Recipe) {
        withContext(ioDispatcher) {
            recipeDao.insertFavorites(recipe = recipe)
        }
    }

    suspend fun getRecipeById(recipeId: Int): Recipe? {
        return withContext(ioDispatcher) {
            try {
                val recipe = recipesApiService.getRecipeById(recipeId)
                recipe
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getRecipesListByCategoryId(categoryId: Int): List<Recipe>? {
        return withContext(ioDispatcher) {
            try {
                val recipesList = recipesApiService.getRecipesListByCategoryId(categoryId)
                recipesList.map { recipe -> recipe.copy(categoryId = categoryId) }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getCategories(): List<Category>? {
        return withContext(ioDispatcher) {
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

