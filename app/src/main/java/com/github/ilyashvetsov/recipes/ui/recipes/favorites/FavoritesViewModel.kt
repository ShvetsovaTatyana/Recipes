package com.github.ilyashvetsov.recipes.ui.recipes.favorites

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.FAVORITES_RECIPE_KEY
import com.github.ilyashvetsov.recipes.ui.SHARED_PREFS_SET_FAVORITES_RECIPE

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<FavoritesScreenState> =
        MutableLiveData(FavoritesScreenState())
    val screenState: LiveData<FavoritesScreenState> = _screenState
    private val recipesRepository = RecipesRepository()

    data class FavoritesScreenState(
        val favoritesList: List<Recipe> = listOf()
    )

    fun getFavorites(): MutableSet<String> {
        val sharedPrefs = getApplication<Application>().getSharedPreferences(
            SHARED_PREFS_SET_FAVORITES_RECIPE,
            MODE_PRIVATE
        )
        val dataSetString = sharedPrefs.getStringSet(FAVORITES_RECIPE_KEY, mutableSetOf())
        val newDataSetString = HashSet(dataSetString ?: mutableSetOf())
        return newDataSetString
    }

    fun getRecipesByIds(ids: String) {
        recipesRepository.getRecipesListByIds(ids, callback = {
            if (it != null)
                _screenState.postValue(FavoritesScreenState(favoritesList = it))
            else
                Toast.makeText(
                    getApplication(),
                    "Ошибка получения данных",
                    Toast.LENGTH_LONG
                ).show()
        })

    }
}