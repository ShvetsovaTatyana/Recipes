package com.github.ilyashvetsov.recipes

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ilyashvetsov.recipes.databinding.ActivityMainBinding
import com.github.ilyashvetsov.recipes.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment() {
    private var _binding:FragmentListCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root

    }

}