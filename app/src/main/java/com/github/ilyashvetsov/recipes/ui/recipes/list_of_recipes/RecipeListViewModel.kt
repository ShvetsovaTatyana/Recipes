package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ilyashvetsov.recipes.model.Recipe
import java.io.IOException

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState: MutableLiveData<RecipeListScreenState> =
        MutableLiveData(RecipeListScreenState())
    val screenState: LiveData<RecipeListScreenState> = _screenState

    data class RecipeListScreenState(
        val categoryId: Int = 0,
        val categoryName: String = "",
        val categoryImageUrl: String = "",
        val categoryImage: Drawable? = null,
        val recipeList: List<Recipe> = listOf()
    )

    fun loadCategory(
        categoryId: Int,
        categoryName: String,
        categoryImageUrl: String
    ): Drawable? {
        //TODO("load from network")
        return try {
            categoryImageUrl.let {
                getApplication<Application>().assets.open(it).use { inputStream ->
                    val drawable = Drawable.createFromStream(inputStream, null)
                    drawable?.let {
                        _screenState.value =
                            screenState.value?.copy(categoryImage = it)
                    }
                    drawable
                }
            }
        } catch (e: IOException) {
            Log.e("LoadDrawable", "Ошибка загрузки drawable из assets: $categoryImageUrl", e)
            null
        }
    }
}