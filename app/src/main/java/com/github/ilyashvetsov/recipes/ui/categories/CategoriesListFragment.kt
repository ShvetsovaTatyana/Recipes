package com.github.ilyashvetsov.recipes.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ilyashvetsov.recipes.databinding.FragmentListCategoriesBinding
import com.github.ilyashvetsov.recipes.model.Category
import com.github.ilyashvetsov.recipes.ui.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesListFragment : Fragment() {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    private val viewModel: CategoriesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
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
        val adapter = CategoriesListAdapter(listOf())
        adapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        binding.rvCategories.adapter = adapter
        viewModel.getCategories()
        viewModel.screenState.observe(viewLifecycleOwner) {
            adapter.dataSet = it.categoriesList
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category =
            viewModel.screenState.value?.categoriesList?.find { category -> category.id == categoryId }
        if (category != null)
            openRecipesByCategory(category)
        else
            throw IllegalStateException()
    }

    private fun openRecipesByCategory(category: Category) {
        findNavController().navigate(
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                category = category
            )
        )
    }
}
