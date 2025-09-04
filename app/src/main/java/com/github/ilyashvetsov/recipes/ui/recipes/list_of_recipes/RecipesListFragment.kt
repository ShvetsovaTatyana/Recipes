package com.github.ilyashvetsov.recipes.ui.recipes.list_of_recipes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.RecipesApplication
import com.github.ilyashvetsov.recipes.databinding.FragmentRecipesListBinding
import com.github.ilyashvetsov.recipes.di.AppContainer
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.ui.UiEvent

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private var category: Category? = null
    private lateinit var viewModel: RecipeListViewModel
    private val argsRecipesListFragment: RecipesListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer: AppContainer =
            (requireActivity().application as RecipesApplication).appContainer
        viewModel = appContainer.recipesListViewModelFactory.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category = argsRecipesListFragment.category
        category?.let {
            viewModel.loadCategory(
                category = it
            )
        }
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
        val adapter = RecipesListAdapter(
            listOf(),
            onItemClick = { openRecipeByRecipeId(id = it) }
        )
        binding.rvCategories.adapter = adapter
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            binding.tvRecipeCategory.text = category?.title
            adapter.dataSetRecipe = state.recipeList
            adapter.notifyDataSetChanged()
            state.categoryImageUrl?.let { url ->
                Glide.with(binding.root)
                    .load(url)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error).into(binding.ivRecipeCategory)
            }
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