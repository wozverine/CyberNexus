package com.glitch.cybernexus.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.glitch.cybernexus.data.source.Database
import com.glitch.cybernexus.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cartAdapter = CartAdapter(
        onCartClicked = ::onCartClicked
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            cartRv.adapter = cartAdapter
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true,
                45.0
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true,
                45.0
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true,
                45.0
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true,
                45.0
            )
            Database.addProduct(
                "company product",
                0.00,
                "descprition",
                "category",
                "imageone",
                "imagetwo",
                "imagethree",
                4.0,
                5,
                true,
                45.0
            )
            cartAdapter.updateList(Database.getProduct())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCartClicked(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }
}