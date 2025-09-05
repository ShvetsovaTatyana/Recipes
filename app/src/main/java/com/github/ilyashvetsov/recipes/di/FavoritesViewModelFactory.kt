package com.github.ilyashvetsov.recipes.di

import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.ui.recipes.favorites.FavoritesViewModel

class FavoritesViewModelFactory(
    private val recipesRepository: RecipesRepository
) : Factory<FavoritesViewModel> {
    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(recipesRepository)
    }

}