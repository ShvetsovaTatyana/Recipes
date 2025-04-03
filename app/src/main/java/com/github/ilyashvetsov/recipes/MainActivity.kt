package com.github.ilyashvetsov.recipes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.ilyashvetsov.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        R.layout.activity_main
        if (savedInstanceState == null) {
            val categoriesFragment = CategoriesListFragment()
            supportFragmentManager.beginTransaction().commit {
                addFragment(R.id.mainContainer, categoriesFragment)
                setReorderingAllowed(true)
            }
        }
    }
}


fun FragmentTransaction.addFragment(containerId: Int, fragment: Fragment) {
    this.add(containerId, fragment)
}

fun FragmentTransaction.commit(block: FragmentTransaction.() -> Unit) {
    block()
    commit()
}

