package com.github.ilyashvetsov.recipes

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration


class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_RECIPE, Recipe::class.java)
            } else {
                it.getParcelable(ARG_RECIPE)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initUI()
    }

    private fun initUI() {
        binding.tvRecipe.text = recipe?.title
        recipe?.imageUrl?.let {
            loadImageFromAssets(
                fileName = it,
                imageView = binding.ivRecipe
            )
        }
    }


    private fun initRecycler() {
        val adapter = recipe?.ingredients?.let { IngredientsAdapter(dataSetIngredient = it) }
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
        divider.dividerThickness = 1
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