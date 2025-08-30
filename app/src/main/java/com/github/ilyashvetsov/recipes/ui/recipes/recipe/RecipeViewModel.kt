package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipeDataBase
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import com.github.ilyashvetsov.recipes.ui.FAVORITES_RECIPE_KEY
import com.github.ilyashvetsov.recipes.ui.SHARED_PREFS_SET_FAVORITES_RECIPE
import com.github.ilyashvetsov.recipes.ui.UiEvent
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<RecipeScreenState> =
        MutableLiveData(RecipeScreenState())
    val screenState: LiveData<RecipeScreenState> = _screenState
    private var recipeId: Int = 0
    private val recipesRepository = RecipesRepository(RecipeDataBase.getInstance(application))

    data class RecipeScreenState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        var portionsCount: Int = 1,
        val recipeImageUrl: String? = null
    )

    val uiEvent = MutableLiveData<UiEvent>()

    fun showToast() {
        uiEvent.postValue(UiEvent.Error("Ошибка получения данных"))
    }

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            val recipe = recipesRepository.getRecipeById(id)
            if (recipe != null) {
                val imageUrl: String =
                    recipe.imageUrl.let { BASE_URL + "images/" + it }
                _screenState.postValue(
                    screenState.value?.copy(
                        recipe = recipe,
                        isFavorite = getFavorites().contains(id.toString()),
                        recipeImageUrl = imageUrl
                    )
                )
                recipeId = id
            } else
                showToast()
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            getApplication<Application>().getSharedPreferences(
                SHARED_PREFS_SET_FAVORITES_RECIPE,
                MODE_PRIVATE
            )
        val dataSetString = sharedPrefs.getStringSet(FAVORITES_RECIPE_KEY, mutableSetOf())
        val newDataSetString = HashSet(dataSetString ?: mutableSetOf())
        return newDataSetString
    }

    fun onFavoritesClicked() {
        val favoritesRecipe = getFavorites()
        if (getFavorites().contains(recipeId.toString())) {
            favoritesRecipe.remove(recipeId.toString())
            saveFavorites(favoritesRecipe)
        } else {
            favoritesRecipe.add(recipeId.toString())
            saveFavorites(favoritesRecipe)
        }
        if (_screenState.value?.isFavorite == true)
            _screenState.value = screenState.value?.copy(isFavorite = false)
        else
            _screenState.value = screenState.value?.copy(isFavorite = true)
    }

    private fun saveFavorites(dataSetString: Set<String>) {
        val sharedPrefs =
            getApplication<Application>().getSharedPreferences(
                SHARED_PREFS_SET_FAVORITES_RECIPE,
                MODE_PRIVATE
            )
        sharedPrefs
            .edit()
            .putStringSet(FAVORITES_RECIPE_KEY, dataSetString)
            .apply()
    }

    fun calculationNumberServings(portionsCount: Int) {
        _screenState.value = screenState.value?.copy(portionsCount = portionsCount)
    }
}

