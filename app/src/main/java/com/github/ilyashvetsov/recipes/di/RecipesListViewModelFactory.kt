package com.github.ilyashvetsov.recipes.di

import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes.RecipeListViewModel

class RecipesListViewModelFactory(
    private val recipesRepository: RecipesRepository
) : Factory<RecipeListViewModel> {
    override fun create(): RecipeListViewModel {
        return RecipeListViewModel(recipesRepository)
    }
}