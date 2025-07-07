package com.github.ilyashvetsov.recipes.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ilyashvetsov.recipes.data.STUB
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe

class CategoriesListViewModel() : ViewModel() {
    private val _screenState: MutableLiveData<CategoriesListScreenState> =
        MutableLiveData(CategoriesListScreenState())
    val screenState: LiveData<CategoriesListScreenState> = _screenState

    data class CategoriesListScreenState(
        val categoriesList: List<Recipe> = listOf()
    )

    fun getCategories():List<Category> {
        val dataSetCategory = STUB.getCategories()
        return dataSetCategory
    }
}