package com.github.ilyashvetsov.recipes.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.databinding.ActivityMainBinding
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.model.Recipe
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnCategories.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.categoriesListFragment)
        }
        binding.btnFavorites.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.favoritesFragment)
        }
        val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
        threadPool.submit {
            val listRecipesFuture = getListAllCategories().map {
                threadPool.submit(Callable { getListRecipesByCategory(it) })
            }
            val recipeList = listRecipesFuture.map { it.get() }
            recipeList.forEach { Log.d("!!!", "Рецепты: $it") }
        }
    }
}

private fun getListAllCategories(): List<Int> {
    val url = URL("https://recipes.androidsprint.ru/api/category")
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()
    val categoryJson = connection.inputStream.bufferedReader().readText()
    Log.d("!!!", "Body: $categoryJson")
    Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
    val categoryListObj = Json.decodeFromString<List<Category>>(categoryJson)
    Log.d("!!!", "$categoryListObj")
    val categoriesListId = categoryListObj.map { it.id }
    return categoriesListId
}

private fun getListRecipesByCategory(categoryId: Int): List<Recipe> {
    val url = URL("https://recipes.androidsprint.ru/api/category/$categoryId/recipes")
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()
    val recipeJson = connection.inputStream.bufferedReader().readText()
    val recipeListObj = Json.decodeFromString<List<Recipe>>(recipeJson)
    return recipeListObj
}

