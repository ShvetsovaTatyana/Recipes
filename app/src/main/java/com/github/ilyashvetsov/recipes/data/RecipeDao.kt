package com.github.ilyashvetsov.recipes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM category;")
    fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM recipe WHERE categoryId=:categoryId;")
    suspend fun getAllRecipes(categoryId: Int): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipe: List<Recipe>)

    @Query("SELECT * FROM recipe WHERE isFavorite=1;")
    suspend fun getFavorites(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(recipe: Recipe)
}
