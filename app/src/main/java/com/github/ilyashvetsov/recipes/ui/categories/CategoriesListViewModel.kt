package com.github.ilyashvetsov.recipes.ui.categories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Category

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
        recipesRepository.getCategories(callback = {
            if (it != null)
                _screenState.postValue(CategoriesListScreenState(categoriesList = it))
            else
                showToast("Ошибка получения данных")
        })
    }
}