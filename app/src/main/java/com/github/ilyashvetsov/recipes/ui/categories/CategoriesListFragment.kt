package com.github.ilyashvetsov.recipes.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ilyashvetsov.recipes.data.STUB
import com.github.ilyashvetsov.recipes.databinding.FragmentListCategoriesBinding
import com.github.ilyashvetsov.recipes.model.Category

class CategoriesListFragment : Fragment() {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private val viewModel by viewModels<CategoriesListViewModel>()

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
    }

    private fun initUI() {
        val adapter = CategoriesListAdapter(listOf())
        adapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        binding.rvCategories.adapter = adapter
        viewModel.screenState.observe(viewLifecycleOwner) {
            val dataSetCategories = viewModel.getCategories()
            adapter.dataSet = dataSetCategories
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category = STUB.getCategories().find { category -> category.id == categoryId }
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
