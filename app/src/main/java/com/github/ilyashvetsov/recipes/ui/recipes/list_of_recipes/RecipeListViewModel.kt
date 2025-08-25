package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipeDataBase
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<RecipeListScreenState> =
        MutableLiveData(RecipeListScreenState())
    val screenState: LiveData<RecipeListScreenState> = _screenState
    private val recipesRepository = RecipesRepository(RecipeDataBase.getInstance(application))

    data class RecipeListScreenState(
        val category: Category? = null,
        val categoryImageUrl: String? = null,
        val recipeList: List<Recipe> = listOf()
    )

    val toastMessage = MutableLiveData<String?>()

    fun showToast(message: String) {
        toastMessage.postValue(message)
    }

    fun loadCategory(category: Category) {
        viewModelScope.launch {
            val recipeList = recipesRepository.getRecipesListByCategoryId(category.id)
            if (recipeList != null) {
                val imageUrl: String =
                    category.imageUrl.let { BASE_URL + "images/" + it }
                _screenState.postValue(
                    recipeList.let {
                        screenState.value?.copy(
                            recipeList = it,
                            categoryImageUrl = imageUrl
                        )
                    })
            } else
                showToast("Ошибка получения данных")
        }
    }
}