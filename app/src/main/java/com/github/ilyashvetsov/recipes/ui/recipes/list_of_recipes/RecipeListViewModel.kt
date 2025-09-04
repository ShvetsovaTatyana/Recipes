package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import com.github.ilyashvetsov.recipes.ui.UiEvent
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {
    private val _screenState: MutableLiveData<RecipeListScreenState> =
        MutableLiveData(RecipeListScreenState())
    val screenState: LiveData<RecipeListScreenState> = _screenState


    data class RecipeListScreenState(
        val category: Category? = null,
        val categoryImageUrl: String? = null,
        val recipeList: List<Recipe> = listOf()
    )

    val uiEvent = MutableLiveData<UiEvent>()

    private fun showToast() {
        uiEvent.postValue(UiEvent.Error("Ошибка получения данных"))
    }

    fun loadCategory(category: Category) {
        viewModelScope.launch {
            val cachedRecipe = recipesRepository.getRecipesFromCache(categoryId = category.id)
            _screenState.postValue(RecipeListScreenState(recipeList = cachedRecipe))
            val recipeList = recipesRepository.getRecipesListByCategoryId(category.id)
            if (recipeList != null) {

                recipesRepository.insertRecipes(recipeList)
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
                showToast()
        }
    }
}