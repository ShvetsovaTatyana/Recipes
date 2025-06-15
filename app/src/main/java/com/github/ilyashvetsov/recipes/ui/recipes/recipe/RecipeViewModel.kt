package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.github.ilyashvetsov.recipes.ui.FavoritesFragment

class RecipeViewModel : ViewModel() {
    data class RecipeScreenState(
        val ingredientsList: List<String> = listOf(),
        val methodList: List<String> = listOf(),
        val isFavorite: Boolean = false,
        val numberServings: Int = 1
    )
}

