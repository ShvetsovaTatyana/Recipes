package com.github.ilyashvetsov.recipes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val ingredient: String
) : Parcelable