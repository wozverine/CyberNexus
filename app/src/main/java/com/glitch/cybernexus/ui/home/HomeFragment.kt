package com.glitch.cybernexus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.glitch.cybernexus.data.source.Database
import com.glitch.cybernexus.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val saleAdapter = SaleAdapter(
        onProductClick = ::onProductClick
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            flashSaleRv.adapter = saleAdapter
            Database.addProduct("sd","ds")
            Database.addProduct("sd","ds")
            Database.addProduct("sd","ds")
            Database.addProduct("sd","ds")
            saleAdapter.updateList(Database.getProduct())
        }
    }

    private fun onProductClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }
    /*private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        with(dialogBinding) {
            button.setOnClickListener() {
                val title = textView.text.toString()
                val desc = textView3.text.toString()

                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    Database.addDailyNotes(title, desc)
                    //binding.dailyNotesRv.adapter = dailyNotesAdapter
                    dailyNotesAdapter.updateList(Database.getDailyNotes())
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(), getString(R.string.fill_blanks), Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        dialog.show()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}