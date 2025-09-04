package com.github.ilyashvetsov.recipes

import android.app.Application
import com.github.ilyashvetsov.recipes.di.AppContainer

class RecipesApplication : Application() {

     lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}