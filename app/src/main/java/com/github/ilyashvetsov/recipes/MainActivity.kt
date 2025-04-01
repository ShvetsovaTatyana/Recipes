package com.github.ilyashvetsov.recipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.ilyashvetsov.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val categoriesFragment = CategoriesListFragment()
            fragmentTransaction.add(
                R.id.mainContainer,
                categoriesFragment
            )
            fragmentTransaction.commit()
        }
    }
}