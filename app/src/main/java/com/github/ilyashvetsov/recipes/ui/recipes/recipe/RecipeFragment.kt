package com.github.ilyashvetsov.recipes.ui.recipes.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private val viewModel by viewModels<RecipeViewModel>()
    private val recipeFragmentArgs: RecipeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        val recipeId = recipeFragmentArgs.recipeId
        viewModel.loadRecipe(recipeId)
    }

    private fun initUI() {
        binding.ibFavorites.setImageResource(R.drawable.ic_heart_empty)
        val adapter = IngredientsAdapter(listOf())
        binding.rvIngredients.adapter = adapter
        val adapterMethod = MethodAdapter(listOf())
        binding.rvMethod.adapter = adapterMethod
        binding.ibFavorites.setOnClickListener {
            viewModel.onFavoritesClicked()
        }
        viewModel.screenState.observe(viewLifecycleOwner) {
            if (it.isFavorite)
                binding.ibFavorites.setImageResource(R.drawable.ic_heart)
            else
                binding.ibFavorites.setImageResource(R.drawable.ic_heart_empty)
            binding.ivRecipe.setImageDrawable(it.recipeImage)
            adapter.updateIngredients(it.portionsCount)
            binding.tvProgress.text = "${it.portionsCount}"
            binding.tvRecipe.text = it.recipe?.title
            val contentDescription = "Изображение рецепта \"${it.recipe?.title}\""
            binding.ivRecipe.contentDescription = contentDescription
            if (it.recipe?.ingredients != null)
                adapter.dataSetIngredient = it.recipe.ingredients
            if (it.recipe?.method != null)
                adapterMethod.dataSetCookingMethod = it.recipe.method
        }
        binding.seekBar.setOnSeekBarChangeListener(
            PortionSeekBarListener(onChangeIngredients = viewModel::calculationNumberServings)
        )
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }
}

class PortionSeekBarListener(val onChangeIngredients: (Int) -> Unit) : OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
        onChangeIngredients(progress)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}