package com.github.ilyashvetsov.recipes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.databinding.ActivityMainBinding


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

//            val listRecipesFuture = getListAllCategories().map {
//                threadPool.submit(Callable { getListRecipesByCategory(it) })
//            }
//            val recipeList = listRecipesFuture.map { it.get() }
//            recipeList.forEach { Log.d("!!!", "Рецепты: $it") }

    }
}

//private fun getListAllCategories(): List<Int> {
//    val logging = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//    val client = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .build()
//    val request: Request = Request.Builder()
//        .url("https://recipes.androidsprint.ru/api/category")
//        .build()
//    client.newCall(request).execute().use {
//        val categoryJson = it.body?.string()
//        val categoryListObj = Json.decodeFromString<List<Category>>(categoryJson.toString())
//        val categoriesListId = categoryListObj.map { it.id }
//        return categoriesListId
//    }
//}
//
//private fun getListRecipesByCategory(categoryId: Int): List<Recipe> {
//    val logging = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//    val client = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .build()
//    val request: Request = Request.Builder()
//        .url("https://recipes.androidsprint.ru/api/category/$categoryId/recipes")
//        .build()
//    client.newCall(request).execute().use {
//        val recipeJson = it.body?.string()
//        val recipeListObj = Json.decodeFromString<List<Recipe>>(recipeJson.toString())
//        return recipeListObj
//    }
//}

