package com.glitch.cybernexus.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.glitch.cybernexus.data.source.Database
import com.glitch.cybernexus.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val categoryFilterAdapter = CategoryFilterAdapter(
        onCategoryFilterClick = ::onCategoryFilterClick
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            categoryRv.adapter = categoryFilterAdapter
            Database.addCategory("Fashion")
            Database.addCategory("Body Enhancement")
            Database.addCategory("Equipment")
            Database.addCategory("Android Hardware")
            categoryFilterAdapter.updateList(Database.getCategory())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryFilterClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }
}