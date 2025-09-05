package com.github.ilyashvetsov.recipes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe

@Database(entities = [Category::class, Recipe::class], version = 1)
@TypeConverters(RecipeConverters::class)
abstract class RecipeDataBase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun categoriesDao(): CategoriesDao
}