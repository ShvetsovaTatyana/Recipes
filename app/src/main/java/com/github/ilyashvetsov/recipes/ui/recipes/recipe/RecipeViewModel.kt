package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ilyashvetsov.recipes.data.STUB
import com.github.ilyashvetsov.recipes.model.Ingredient
import com.github.ilyashvetsov.recipes.ui.FAVORITES_RECIPE_KEY
import com.github.ilyashvetsov.recipes.ui.SHARED_PREFS_SET_FAVORITES_RECIPE

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<RecipeScreenState> =
        MutableLiveData(RecipeScreenState())
    val screenState: LiveData<RecipeScreenState> = _screenState
    private var recipeId: Int = 0

    data class RecipeScreenState(
        val ingredientsList: List<Ingredient> = listOf(),
        val methodList: List<String> = listOf(),
        val isFavorite: Boolean = false,
        val portionsCount: Int = 1,
    )

    fun loadRecipe(id: Int) {
        //TODO("load from network")
        val recipe = STUB.getRecipeById(id)
        if (recipe != null) {
            _screenState.value = RecipeScreenState(
                ingredientsList = recipe.ingredients,
                methodList = recipe.method,
                isFavorite = getFavorites().contains(id.toString()),
                portionsCount = 1
            )
        }
        recipeId = id
    }

    fun getFavorites(): MutableSet<String> {
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
            _screenState.value = _screenState.value?.copy(isFavorite = false)
        else
            _screenState.value = _screenState.value?.copy(isFavorite = true)
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
}

