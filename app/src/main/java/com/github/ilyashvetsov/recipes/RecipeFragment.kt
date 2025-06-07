package com.github.ilyashvetsov.recipes

import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
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
        val contentDescription = "Изображение рецепта \"${recipe?.title}\""
        binding.ivRecipe.contentDescription = contentDescription
        binding.ibFavorites.setImageResource(R.drawable.ic_heart_empty)
        binding.ibFavorites.setOnClickListener {
            colorTheHeart()
            changeFavorites()
        }
        if (getFavorites().contains(recipe?.id.toString()))
            binding.ibFavorites.setImageResource(R.drawable.ic_heart)
    }

    private fun colorTheHeart() {
        if (!getFavorites().contains(recipe?.id.toString())) {
            binding.ibFavorites.setImageResource(R.drawable.ic_heart)
        } else
            binding.ibFavorites.setImageResource(R.drawable.ic_heart_empty)
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

    private fun saveFavorites(dataSetString: Set<String>) {
        val sharedPrefs =
            requireActivity().getSharedPreferences(SHARED_PREFS_SET_FAVORITES_RECIPE, MODE_PRIVATE)
        sharedPrefs
            .edit()
            .putStringSet(FAVORITES_RECIPE_KEY, dataSetString)
            .apply()
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            requireActivity().getSharedPreferences(SHARED_PREFS_SET_FAVORITES_RECIPE, MODE_PRIVATE)
        val dataSetString = sharedPrefs.getStringSet(FAVORITES_RECIPE_KEY, mutableSetOf())
        val newDataSetString = HashSet(dataSetString ?: mutableSetOf())
        return newDataSetString
    }

    private fun changeFavorites() {
        val favoritesRecipe = getFavorites()
        if (getFavorites().contains(recipe?.id.toString())) {
            favoritesRecipe.remove(recipe?.id.toString())
            saveFavorites(favoritesRecipe)
        } else {
            favoritesRecipe.add(recipe?.id.toString())
            saveFavorites(favoritesRecipe)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }
}