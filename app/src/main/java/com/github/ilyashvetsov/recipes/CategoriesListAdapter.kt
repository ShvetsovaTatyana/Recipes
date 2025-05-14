package com.github.ilyashvetsov.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ilyashvetsov.recipes.databinding.ItemCategoryBinding


class CategoriesListAdapter(
    private val dataSet: List<Category>,
) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryItemBinding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(categoryItemBinding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], itemClickListener)
    }

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category, itemClickListener: OnItemClickListener?) {
            binding.tvCategoryDescription.text = item.description
            binding.tvTitleForCategory.text = item.title
            loadImageFromAssets(item.imageUrl, binding.ivCategory)
            val categoryImage = itemView.context.getString(R.string.category_image, item.title)
            binding.ivCategory.contentDescription = categoryImage
            itemView.setOnClickListener { itemClickListener?.onItemClick(item.id) }
        }
    }
}