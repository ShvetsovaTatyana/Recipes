package com.github.ilyashvetsov.recipes.ui.recipes.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ilyashvetsov.recipes.databinding.FragmentFavoritesBinding
import com.github.ilyashvetsov.recipes.ui.UiEvent
import com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes.RecipesListAdapter

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
        viewModel.uiEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is UiEvent.Error -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initUI() {
        val adapter = RecipesListAdapter(listOf(), onItemClick = { openRecipeByRecipeId(id = it) })
        binding.rvFavorites.adapter = adapter
        viewModel.getFavoritesRecipes()
        viewModel.screenState.observe(viewLifecycleOwner) {
            adapter.dataSetRecipe = it.favoritesList
            adapter.notifyDataSetChanged()
            if (it.favoritesList.isEmpty()) {
                binding.tvPlaceholder.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.rvFavorites.visibility = View.VISIBLE
                binding.tvPlaceholder.visibility = View.GONE
            }
        }
    }

    private fun openRecipeByRecipeId(id: Int) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(
                id
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

