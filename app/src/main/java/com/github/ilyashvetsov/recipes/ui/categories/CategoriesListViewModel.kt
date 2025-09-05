package com.github.ilyashvetsov.recipes.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.ui.UiEvent
import kotlinx.coroutines.launch

class CategoriesListViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _screenState: MutableLiveData<CategoriesListScreenState> =
        MutableLiveData(CategoriesListScreenState())
    val screenState: LiveData<CategoriesListScreenState> = _screenState

    data class CategoriesListScreenState(
        val categoriesList: List<Category> = listOf()
    )

    val uiEvent = MutableLiveData<UiEvent>()

    private fun showToast() {
        uiEvent.postValue(UiEvent.Error("Ошибка получения данных"))
    }

    fun getCategories() {
        viewModelScope.launch {
            val cachedCategory = recipesRepository.getCategoriesFromCache()
            _screenState.postValue(CategoriesListScreenState(categoriesList = cachedCategory))
            val categoryList = recipesRepository.getCategories()
            if (categoryList != null) {
                recipesRepository.insertCategories(categoryList)
                _screenState.postValue(CategoriesListScreenState(categoryList))
            } else
                showToast()
        }
    }
}
