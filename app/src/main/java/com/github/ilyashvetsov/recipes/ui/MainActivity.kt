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

    }
}

