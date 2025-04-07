package com.github.ilyashvetsov.recipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.github.ilyashvetsov.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CategoriesListFragment>(R.id.mainContainer)
            }
        }

        binding.btnCategories.setOnClickListener {
            supportFragmentManager.commit {
                replace<CategoriesListFragment>(
                    R.id.mainContainer
                )
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
        binding.btnFavorites.setOnClickListener {
            supportFragmentManager.commit {
                replace<FavoritesFragment>(
                    R.id.mainContainer
                )
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

    }
}

