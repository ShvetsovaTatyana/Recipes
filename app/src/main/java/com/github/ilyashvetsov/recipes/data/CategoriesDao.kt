package com.github.ilyashvetsov.recipes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.ilyashvetsov.recipes.model.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category;")
    fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>)

}