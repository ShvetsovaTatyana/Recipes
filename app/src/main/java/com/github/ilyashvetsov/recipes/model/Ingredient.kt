package com.github.ilyashvetsov.recipes.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "ingredient")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "quantity") val quantity: String,
    @ColumnInfo(name = "unitOfMeasure") val unitOfMeasure: String,
    @ColumnInfo(name = "description") val description: String
) : Parcelable