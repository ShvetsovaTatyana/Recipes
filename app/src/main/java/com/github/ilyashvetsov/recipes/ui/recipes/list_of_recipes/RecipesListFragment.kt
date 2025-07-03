package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipesListBinding
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_ID
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_IMAGE_URL
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_NAME
import com.github.ilyashvetsov.recipes.ui.ARG_RECIPE_ID
import com.github.ilyashvetsov.recipes.ui.recipes.recipe.RecipeFragment

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
        initUI()
        val categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryId?.let {
            viewModel.loadCategory(
                categoryId = it,
                categoryName = categoryName ?: "",
                categoryImageUrl = categoryImageUrl ?: ""
            )
        }
        initBundleData()
    }

    private fun initUI() {
        val adapter = RecipesListAdapter(
            listOf(),
            onItemClick = { openRecipeByRecipeId(id = it) }
        )
        binding.rvCategories.adapter = adapter
        viewModel.screenState.observe(viewLifecycleOwner) {
            binding.ivRecipeCategory.setImageDrawable(it.categoryImage)
            binding.tvRecipeCategory.text = categoryName
            adapter.dataSetRecipe = it.recipeList

        }
    }

    private fun initBundleData() {
        categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryName = arguments?.getString(ARG_CATEGORY_NAME)
        categoryImageUrl = arguments?.getString(ARG_CATEGORY_IMAGE_URL)
    }

    private fun openRecipeByRecipeId(id: Int) {
        val bundle = bundleOf(ARG_RECIPE_ID to id)
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