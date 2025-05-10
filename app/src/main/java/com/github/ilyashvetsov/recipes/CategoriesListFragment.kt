package com.github.ilyashvetsov.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.github.ilyashvetsov.recipes.databinding.FragmentListCategoriesBinding

const val ARG_CATEGORY_ID = "ARG_CATEGORY_ID"
const val ARG_CATEGORY_NAME = "ARG_CATEGORY_NAME"
const val ARG_CATEGORY_IMAGE_URL = "ARG_CATEGORY_IMAGE_URL"

class CategoriesListFragment : Fragment() {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initRecycler() {
        val dataSet = STUB.getCategories()
        val adapter = CategoriesListAdapter(dataSet)
        adapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        binding.rvCategories.adapter = adapter
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val categoryName =
            STUB.getCategories().filter { category -> category.id == categoryId }[0].title
        val categoryImageUrl =
            STUB.getCategories().filter { category -> category.id == categoryId }[0].imageUrl
        val bundle = Bundle()
        bundle.putInt(ARG_CATEGORY_ID, categoryId)
        bundle.putString(ARG_CATEGORY_NAME, categoryName)
        bundle.putString(ARG_CATEGORY_IMAGE_URL, categoryImageUrl)
        parentFragmentManager.commit {
            replace<RecipesListFragment>(
                R.id.mainContainer,
                args = bundle
            )
            setReorderingAllowed(true)
            addToBackStack(null)
        }

    }
}
