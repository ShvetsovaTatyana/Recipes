package com.github.ilyashvetsov.recipes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.databinding.ItemCategoryRecipesBinding

class CategoryListRecipesAdapter(
    private val dataSetRecipe: List<Recipe>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoryListRecipesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryRecipesBinding =
            ItemCategoryRecipesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(categoryRecipesBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSetRecipe[position])
    }

    override fun getItemCount(): Int {
        return dataSetRecipe.size
    }

    class ViewHolder(
        private val binding: ItemCategoryRecipesBinding,
        private val onItemClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe) {
            binding.tvNameDish.text = item.title
            loadImageFromAssets(item.imageUrl, binding.ivDish)
            val categoryImage = itemView.context.getString(R.string.category_image, item.title)
            binding.ivDish.contentDescription = categoryImage
            itemView.setOnClickListener { onItemClick.invoke(item.id) }
        }
    }
}



