package com.github.ilyashvetsov.recipes.ui.recipes.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.UiEvent
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _screenState: MutableLiveData<FavoritesScreenState> =
        MutableLiveData(FavoritesScreenState())
    val screenState: LiveData<FavoritesScreenState> = _screenState

    data class FavoritesScreenState(
        val favoritesList: List<Recipe> = listOf()
    )

    val uiEvent = MutableLiveData<UiEvent>()

    private suspend fun getFavorites(): List<Recipe> {
        return recipesRepository.getFavoritesFromCache()
    }

    fun getFavoritesRecipes() {
        viewModelScope.launch {
            val favoritesList = getFavorites()
            _screenState.postValue(FavoritesScreenState(favoritesList = favoritesList))
        }
    }
}