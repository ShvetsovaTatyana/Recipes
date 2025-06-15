package com.github.ilyashvetsov.recipes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.data.STUB
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipesListBinding
import com.github.ilyashvetsov.recipes.ui.recipes.recipe.RecipeFragment

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundleData()
        initRecycler()
        initFragmentHeader()
    }

    private fun initBundleData() {
        categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryName = arguments?.getString(ARG_CATEGORY_NAME)
        categoryImageUrl = arguments?.getString(ARG_CATEGORY_IMAGE_URL)
    }

    private fun initFragmentHeader() {
        binding.tvRecipeCategory.text = categoryName
        categoryImageUrl?.let {
            loadImageFromAssets(
                fileName = it,
                imageView = binding.ivRecipeCategory
            )
        }
    }

    private fun initRecycler() {
        val dataSetRecipe = categoryId?.let { STUB.getRecipesByCategoryId(it) }
        val adapter = dataSetRecipe?.let {
            CategoryListRecipesAdapter(
                it,
                onItemClick = { openRecipeByRecipeId(id = it) }
            )
        }
        binding.rvCategories.adapter = adapter
    }

    private fun openRecipeByRecipeId(id: Int) {
        val recipe = STUB.getRecipeById(id)
        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            replace<RecipeFragment>(
                R.id.mainContainer,
                args = bundle
            )
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}