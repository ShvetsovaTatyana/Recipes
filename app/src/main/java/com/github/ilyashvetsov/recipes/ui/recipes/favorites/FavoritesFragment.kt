package com.github.ilyashvetsov.recipes.ui.recipes.favorites

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
import com.github.ilyashvetsov.recipes.data.STUB
import com.github.ilyashvetsov.recipes.databinding.FragmentFavoritesBinding
import com.github.ilyashvetsov.recipes.ui.ARG_RECIPE_ID
import com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes.RecipesListAdapter
import com.github.ilyashvetsov.recipes.ui.recipes.recipe.RecipeFragment

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val adapter = RecipesListAdapter(listOf(), onItemClick = { openRecipeByRecipeId(id = it) })
        binding.rvFavorites.adapter = adapter
        viewModel.screenState.observe(viewLifecycleOwner) {
            val dataSetRecipe = viewModel.getFavorites()
            adapter.dataSetRecipe = STUB.getRecipesByIds(dataSetRecipe.map { it.toInt() }.toSet())
            if (dataSetRecipe.isEmpty()) {
                binding.tvPlaceholder.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.rvFavorites.visibility = View.VISIBLE
                binding.tvPlaceholder.visibility = View.GONE
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

