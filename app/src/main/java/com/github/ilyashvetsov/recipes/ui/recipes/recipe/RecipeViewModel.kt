package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import androidx.lifecycle.ViewModel

class RecipeViewModel : ViewModel() {
    data class RecipeScreenState(
        val ingredientsList: List<String> = listOf(),
        val methodList: List<String> = listOf()
    )
}

