package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.recipes.databinding.ItemIngredientsBinding
import com.github.ilyashvetsov.recipes.model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(
    private val dataSetIngredient: List<Ingredient>,
    private var quantity: Int = 1
) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val ingredientsItemBinding = ItemIngredientsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(ingredientsItemBinding)
    }

    override fun getItemCount(): Int {
        return dataSetIngredient.size
    }

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSetIngredient[position], quantity)
    }

    class ViewHolder(private val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ingredient, quantity: Int) {
            binding.tvIngredient.text = item.ingredient
            val displayQuantity = BigDecimal(item.quantity)
                .multiply(BigDecimal(quantity))
                .setScale(1, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()
            binding.tvAmountOfIngredient.text = "$displayQuantity ${item.unitOfMeasure}"
        }
    }
}