package com.github.ilyashvetsov.recipes.ui.recipes.favorites

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ilyashvetsov.recipes.data.RecipesRepository
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.FAVORITES_RECIPE_KEY
import com.github.ilyashvetsov.recipes.ui.SHARED_PREFS_SET_FAVORITES_RECIPE
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<FavoritesScreenState> =
        MutableLiveData(FavoritesScreenState())
    val screenState: LiveData<FavoritesScreenState> = _screenState
    private val recipesRepository = RecipesRepository()

    data class FavoritesScreenState(
        val favoritesList: List<Recipe> = listOf()
    )

    val toastMessage = MutableLiveData<String?>()

    fun showToast(message: String) {
        toastMessage.postValue(message)
    }

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
        viewModelScope.launch {
            val favoritesList = recipesRepository.getRecipesListByIds(ids)
            if (favoritesList != null)
                _screenState.postValue(FavoritesScreenState(favoritesList = favoritesList))
            else
                showToast("Ошибка получения данных")
        }
    }
}