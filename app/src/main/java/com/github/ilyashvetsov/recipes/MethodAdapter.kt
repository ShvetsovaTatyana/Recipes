package com.github.ilyashvetsov.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.recipes.databinding.ItemCookingMethodBinding


class MethodAdapter(
    private val dataSetCookingMethod: List<String>,
) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cookingMethodItemBinding =
            ItemCookingMethodBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(cookingMethodItemBinding)
    }

    override fun getItemCount(): Int {
        return dataSetCookingMethod.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSetCookingMethod[position])
    }

    class ViewHolder(private val binding: ItemCookingMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvCookingMethod.text = "${getAdapterPosition() + 1}. $item"

        }
    }
}