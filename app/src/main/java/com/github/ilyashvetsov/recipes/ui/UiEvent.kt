package com.github.ilyashvetsov.recipes.ui

sealed class UiEvent {
    data class Error(val message: String) : UiEvent()
}