package com.github.ilyashvetsov.recipes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.ilyashvetsov.recipes.model.Category

@Database(entities = [Category::class], version = 1)
abstract class RecipeDataBase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        private var instance: RecipeDataBase? = null

        fun getInstance(context: Context): RecipeDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDataBase::class.java,
                    "app_database"
                ).build()
            }
            return instance!!
        }
    }
}