package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipeBinding
import com.github.ilyashvetsov.recipes.model.Recipe
import com.github.ilyashvetsov.recipes.ui.ARG_RECIPE_ID
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private var recipe: Recipe? = null
    private val viewModel by viewModels<RecipeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initUI()
        val recipeId = arguments?.getInt(ARG_RECIPE_ID)
        recipeId?.let { viewModel.loadRecipe(it) }
    }

    private fun initUI() {
        binding.tvRecipe.text = recipe?.title
        val contentDescription = "Изображение рецепта \"${recipe?.title}\""
        binding.ivRecipe.contentDescription = contentDescription
        binding.ibFavorites.setImageResource(R.drawable.ic_heart_empty)
        binding.ibFavorites.setOnClickListener {
            viewModel.onFavoritesClicked()
        }
        if (viewModel.getFavorites().contains(recipe?.id.toString()))
            binding.ibFavorites.setImageResource(R.drawable.ic_heart)
        viewModel.screenState.observe(viewLifecycleOwner) {
            if (it.isFavorite)
                binding.ibFavorites.setImageResource(R.drawable.ic_heart)
            else
                binding.ibFavorites.setImageResource(R.drawable.ic_heart_empty)
            binding.ivRecipe.setImageDrawable(it.recipeImage)
        }
    }

    private fun initRecycler() {
        val adapter =
            recipe?.ingredients?.let { IngredientsAdapter(dataSetIngredient = it) }
        binding.rvIngredients.adapter = adapter
        val adapterMethod = recipe?.method?.let { MethodAdapter(dataSetCookingMethod = it) }
        binding.rvMethod.adapter = adapterMethod
        val divider = MaterialDividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        divider.setDividerColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.list_separator
            )
        )
        divider.isLastItemDecorated = false
        divider.dividerThickness = resources.getDimensionPixelSize(R.dimen.space_1)
        divider.dividerInsetStart = resources.getDimensionPixelOffset(R.dimen.space_12)
        divider.dividerInsetEnd = resources.getDimensionPixelOffset(R.dimen.space_12)
        binding.rvIngredients.addItemDecoration(divider)
        binding.rvMethod.addItemDecoration(divider)
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                adapter?.updateIngredients(progress)
                binding.tvProgress.text = "$progress"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }
}