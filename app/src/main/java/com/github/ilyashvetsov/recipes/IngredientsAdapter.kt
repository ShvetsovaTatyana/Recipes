package com.github.ilyashvetsov.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.recipes.databinding.ItemIngredientsBinding

class IngredientsAdapter(
    private val dataSetIngredient: List<Ingredient>,
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSetIngredient[position])
    }

    class ViewHolder(private val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ingredient) {
            binding.tvIngredient.text = item.ingredient
            binding.tvAmountOfIngredient.text = "${item.quantity} ${item.unitOfMeasure}"
        }
    }
}