package com.github.ilyashvetsov.recipes.di

import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.ui.recipes.recipe.RecipeViewModel

class RecipeViewModelFactory(
    private val recipesRepository: RecipesRepository
) : Factory<RecipeViewModel> {
    override fun create(): RecipeViewModel {
        return RecipeViewModel(recipesRepository)
    }
}