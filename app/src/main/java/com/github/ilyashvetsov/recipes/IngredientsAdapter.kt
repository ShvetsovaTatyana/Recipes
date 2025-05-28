package com.github.ilyashvetsov.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.recipes.databinding.ItemIngredientsBinding

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
            val totalQuantity = item.quantity.toDouble() * quantity
            if (totalQuantity.rem(1.0) != 0.0) {
                val totalQuantityFormat = String.format("%.1f", totalQuantity)
                binding.tvAmountOfIngredient.text =
                    "$totalQuantityFormat ${item.unitOfMeasure}"
            } else
                binding.tvAmountOfIngredient.text =
                    "${totalQuantity.toInt()} ${item.unitOfMeasure}"
        }


    }
}