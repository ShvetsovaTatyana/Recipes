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
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

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
        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val categoryJson = connection.inputStream.bufferedReader().readText()
            Log.d("!!!", "Body: $categoryJson")
            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            val categoryListObj = Json.decodeFromString<List<Category>>(categoryJson)
            Log.d("!!!", "$categoryListObj")
        }
        thread.start()
        Log.i("!!!", "Метод onCreate() выполняется на потоке:${Thread.currentThread().name} ")
    }
}

