package com.github.ilyashvetsov.recipes

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.recipes.databinding.ItemCategoryBinding
import java.io.IOException


class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryItemBinding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(categoryItemBinding, parent.context)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class ViewHolder(private val binding: ItemCategoryBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.tvCategoryDescription.text = item.description
            binding.tvTitleForCategory.text = item.title
            loadImageFromAssets(item.imageUrl, binding.ivCategory)
            val categoryImage = context.getString(R.string.category_image)
            binding.ivCategory.contentDescription =
                String.format(categoryImage, item.title)
        }

        private fun loadImageFromAssets(fileName: String, imageView: ImageView) {
            val context = imageView.context
            val assetManager = context.assets
            try {
                val inputStream = assetManager.open(fileName)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageView.setImageBitmap(bitmap)
                inputStream.close()
            } catch (e: IOException) {
                Log.e("ImageLoadError", "Image not found: ${fileName}", e)
            }
        }

    }
}