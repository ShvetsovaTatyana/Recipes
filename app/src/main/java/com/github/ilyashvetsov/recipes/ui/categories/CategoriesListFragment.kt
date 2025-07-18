package com.github.ilyashvetsov.recipes.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.ilyashvetsov.recipes.R
import com.github.ilyashvetsov.recipes.data.STUB
import com.github.ilyashvetsov.recipes.databinding.FragmentListCategoriesBinding
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_ID
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_IMAGE_URL
import com.github.ilyashvetsov.recipes.ui.ARG_CATEGORY_NAME

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
        val categoryName =
            STUB.getCategories().filter { category -> category.id == categoryId }[0].title
        val categoryImageUrl =
            STUB.getCategories().filter { category -> category.id == categoryId }[0].imageUrl
        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl,
        )
        findNavController().navigate(
            R.id.action_categoriesListFragment_to_recipesListFragment,
            bundle
        )
    }
}
