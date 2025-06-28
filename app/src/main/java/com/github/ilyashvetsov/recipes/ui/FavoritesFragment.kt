package com.github.ilyashvetsov.recipes.ui

import android.content.Context.MODE_PRIVATE
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
import com.github.ilyashvetsov.recipes.databinding.FragmentFavoritesBinding
import com.github.ilyashvetsov.recipes.ui.recipes.recipe.RecipeFragment

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val dataSetRecipe = getFavorites()
        if (dataSetRecipe.isEmpty()) {
            binding.tvPlaceholder.visibility = View.VISIBLE
            binding.rvFavorites.visibility = View.GONE
        } else {
            binding.rvFavorites.visibility = View.VISIBLE
            binding.tvPlaceholder.visibility = View.GONE
        }
        val adapter = CategoryListRecipesAdapter(
            STUB.getRecipesByIds(dataSetRecipe.map { it.toInt() }.toSet()),
            onItemClick = { openRecipeByRecipeId(id = it) }
        )
        binding.rvFavorites.adapter = adapter
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

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            requireActivity().getSharedPreferences(SHARED_PREFS_SET_FAVORITES_RECIPE, MODE_PRIVATE)
        val dataSetString = sharedPrefs.getStringSet(FAVORITES_RECIPE_KEY, mutableSetOf())
        val newDataSetString = HashSet(dataSetString ?: mutableSetOf())
        return newDataSetString
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

