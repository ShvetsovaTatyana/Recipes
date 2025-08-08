package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<RecipeListScreenState> =
        MutableLiveData(RecipeListScreenState())
    val screenState: LiveData<RecipeListScreenState> = _screenState
    private val recipesRepository = RecipesRepository()

    data class RecipeListScreenState(
        val category: Category? = null,
        val categoryImageUrl: String? = null,
        val recipeList: List<Recipe> = listOf()
    )

    val toastMessage = MutableLiveData<String?>()

    fun showToast(message: String) {
        toastMessage.value = message
    }

    fun loadCategory(
        category: Category,
    ) {
        recipesRepository.getRecipesListByCategoryId(category.id, callback = {
            if (it != null) {
                val imageUrl: String =
                    category.imageUrl.let { BASE_URL + "images/" + it }
                _screenState.postValue(
                    it.let {
                        screenState.value?.copy(
                            recipeList = it,
                            categoryImageUrl = imageUrl
                        )
                    })
            } else
                showToast("Ошибка получения данных")
        })
    }
}