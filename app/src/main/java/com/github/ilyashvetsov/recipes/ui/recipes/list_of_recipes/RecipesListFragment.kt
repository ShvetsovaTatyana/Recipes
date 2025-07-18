package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipesListBinding
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_ID
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_IMAGE_URL
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_NAME
import com.github.ilyashvetsov.recipes.ui.ARG_RECIPE_ID

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null
    private val viewModel by viewModels<RecipeListViewModel>()

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
        val categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryId?.let {
            viewModel.loadCategory(
                categoryId = it,
                categoryImageUrl = categoryImageUrl ?: ""
            )
        }
        initUI()
    }

    private fun initUI() {
        val adapter = RecipesListAdapter(
            listOf(),
            onItemClick = { openRecipeByRecipeId(id = it) }
        )
        binding.rvCategories.adapter = adapter
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            binding.ivRecipeCategory.setImageDrawable(state.categoryImage)
            binding.tvRecipeCategory.text = categoryName
            adapter.dataSetRecipe = state.recipeList

        }
    }

    private fun initBundleData() {
        categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryName = arguments?.getString(ARG_CATEGORY_NAME)
        categoryImageUrl = arguments?.getString(ARG_CATEGORY_IMAGE_URL)
    }

    private fun openRecipeByRecipeId(id: Int) {
        findNavController().navigate(
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(
                recipeId = id
            )
        )

    }
}