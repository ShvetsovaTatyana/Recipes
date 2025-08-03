package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipesListBinding
import com.github.ilyashvetsov.recipes.model.Category

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private var category: Category? = null
    private val viewModel by viewModels<RecipeListViewModel>()
    private val argsRecipesListFragment: RecipesListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category = argsRecipesListFragment.category
        category?.id?.let {
            viewModel.loadCategory(
                categoryId = it,
                categoryImageUrl = category?.imageUrl ?: ""
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
            binding.tvRecipeCategory.text = category?.title
            adapter.dataSetRecipe = state.recipeList
            adapter.notifyDataSetChanged()
        }
    }

    private fun openRecipeByRecipeId(id: Int) {
        findNavController().navigate(
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(
                recipeId = id
            )
        )

    }
}