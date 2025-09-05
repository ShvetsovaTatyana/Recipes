package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import com.github.ilyashvetsov.recipes.ui.UiEvent
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {
    private val _screenState: MutableLiveData<RecipeScreenState> =
        MutableLiveData(RecipeScreenState())
    val screenState: LiveData<RecipeScreenState> = _screenState
    private var recipeId: Int = 0

    data class RecipeScreenState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        var portionsCount: Int = 1,
        val recipeImageUrl: String? = null
    )

    val uiEvent = MutableLiveData<UiEvent>()

    private fun showToast() {
        uiEvent.postValue(UiEvent.Error("Ошибка получения данных"))
    }

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            val recipe = recipesRepository.getRecipeById(id)
            if (recipe != null) {
                recipeId = id
                val favoritesRecipe = getFavorites()
                val recipeIsFavorites = favoritesRecipe.find { it.id == recipeId }
                val imageUrl: String =
                    recipe.imageUrl.let { BASE_URL + "images/" + it }
                _screenState.postValue(
                    screenState.value?.copy(
                        recipe = recipe,
                        isFavorite = recipeIsFavorites != null,
                        recipeImageUrl = imageUrl
                    )
                )
            } else
                showToast()
        }
    }

    private suspend fun getFavorites(): List<Recipe> {
        val favoritesCash = recipesRepository.getFavoritesFromCache()
        return favoritesCash
    }


    fun onFavoritesClicked() {
        viewModelScope.launch {
            val favoritesRecipe = getFavorites()
            val recipeIsFavorites = favoritesRecipe.find { recipe -> recipe.id == recipeId }
            if (recipeIsFavorites != null) {
                recipeIsFavorites.isFavorite = false
                recipesRepository.insertFavorites(recipeIsFavorites)
            } else {
                val recipeNoFavorites = screenState.value?.recipe
                recipeNoFavorites?.isFavorite = true
                recipeNoFavorites?.let { recipesRepository.insertFavorites(it) }
            }
            if (_screenState.value?.isFavorite == true)
                _screenState.value = screenState.value?.copy(isFavorite = false)
            else
                _screenState.value = screenState.value?.copy(isFavorite = true)
        }
    }

    fun calculationNumberServings(portionsCount: Int) {
        _screenState.value = screenState.value?.copy(portionsCount = portionsCount)
    }
}

