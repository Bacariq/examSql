package com.example.sqlrecap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlrecap.adapter.DepenseAdapter
import com.example.sqlrecap.databinding.FragmentDepensesListeBinding
import com.example.sqlrecap.model.DepenseWithType
import com.example.sqlrecap.viewModel.DepenseViewModel
import androidx.navigation.fragment.findNavController
import com.example.sqlrecap.viewModel.DepensesArrray


class DepensesListeFragment : Fragment(), DepenseAdapter.OnLikeClickListener {
    private lateinit var depenseViewModel: DepenseViewModel
    private lateinit var adapter: DepenseAdapter
    lateinit var binding: FragmentDepensesListeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDepensesListeBinding.inflate(layoutInflater)
        depenseViewModel = ViewModelProvider(requireActivity()).get(DepenseViewModel::class.java)
        //depenseViewModel.setDepense(requireActivity())
        depenseViewModel.getDepenses(requireActivity()).observeForever {
            updateData(it)
        }
        setupButtonListeners()
        return binding.root
    }

    private fun setupButtonListeners() {
        binding.btnAddDepense.setOnClickListener {
            val action = DepensesListeFragmentDirections.actionDepensesListeFragmentToDepenseAddFragment(0)
            findNavController().navigate(action)
        }
    }

    private fun updateData(depenses: List<DepenseWithType>) {
        DepensesArrray.depenses = depenses
        DepensesArrray.depenses?.let { it ->
            adapter = DepenseAdapter(it, this)
            binding.myMoviesRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
            binding.myMoviesRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onLikeClick(depenseWithType: DepenseWithType) {
        Log.d("LM","onLikeClick" + depenseWithType.depense.nom)
        val action = DepensesListeFragmentDirections.actionDepensesListeFragmentToDepenseAddFragment(depenseWithType.depense.depenseId)
        findNavController().navigate(action)
    }
}