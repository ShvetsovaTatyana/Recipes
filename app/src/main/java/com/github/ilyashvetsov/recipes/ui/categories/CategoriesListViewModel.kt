package com.github.ilyashvetsov.recipes.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Category
import kotlinx.coroutines.launch

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<CategoriesListScreenState> =
        MutableLiveData(CategoriesListScreenState())
    val screenState: LiveData<CategoriesListScreenState> = _screenState
    private val recipesRepository = RecipesRepository()

    data class CategoriesListScreenState(
        val categoriesList: List<Category> = listOf()
    )

    val toastMessage = MutableLiveData<String?>()

    fun showToast(message: String) {
        toastMessage.value = message
    }

    fun getCategories() {
        viewModelScope.launch {
            val categoryList = recipesRepository.getCategories()
            if (categoryList != null)
                _screenState.postValue(CategoriesListScreenState(categoriesList = categoryList))
            else
                showToast("Ошибка получения данных")
        }
    }
}