package com.github.ilyashvetsov.recipes.di

import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.ui.categories.CategoriesListViewModel

class CategoriesListViewModelFactory(
    private val recipesRepository: RecipesRepository,
) : Factory<CategoriesListViewModel> {
    override fun create(): CategoriesListViewModel {
        return CategoriesListViewModel(recipesRepository)
    }
}