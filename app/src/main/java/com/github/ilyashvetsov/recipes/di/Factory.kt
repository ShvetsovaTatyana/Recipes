package com.github.ilyashvetsov.recipes.di

interface Factory<T> {
    fun create():T
}