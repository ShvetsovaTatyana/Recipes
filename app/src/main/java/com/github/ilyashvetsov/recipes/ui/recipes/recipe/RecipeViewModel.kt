package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipeViewModel : ViewModel() {
    private val _screenState: MutableLiveData<RecipeScreenState> =
        MutableLiveData(RecipeScreenState())
    val screenState: LiveData<RecipeScreenState> = _screenState

    data class RecipeScreenState(
        val ingredientsList: List<String> = listOf(),
        val methodList: List<String> = listOf(),
        val isFavorite: Boolean = false,
        val numberServings: Int = 1
    )

    init {
        Log.d("RecipeViewModel", "Инициализация прошла успешно")
        _screenState.value = RecipeScreenState(isFavorite = true)
    }
}

