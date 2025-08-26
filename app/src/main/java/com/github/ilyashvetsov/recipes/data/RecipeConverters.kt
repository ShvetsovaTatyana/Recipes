package com.github.ilyashvetsov.recipes.data

import androidx.room.TypeConverter
import com.github.ilyashvetsov.recipes.model.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeConverters {
    @TypeConverter
    fun fromIngredientsList(ingredients: List<Ingredient>): String {
        return Gson().toJson(ingredients)
    }

    @TypeConverter
    fun toIngredientsList(ingredientsString: String): List<Ingredient> {
        return Gson().fromJson(ingredientsString, object : TypeToken<List<Ingredient>>() {}.type)
    }

    @TypeConverter
    fun fromMethodsList(methods: List<String>): String {
        return Gson().toJson(methods)
    }

    @TypeConverter
    fun toMethodsList(methodsString: String): List<String> {
        return Gson().fromJson(methodsString, object : TypeToken<List<String>>() {}.type)
    }
}