package com.example.sqlrecap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sqlrecap.databinding.FragmentDepenseAddBinding
import com.example.sqlrecap.databinding.FragmentDepensesListeBinding
import com.example.sqlrecap.model.Depense
import com.example.sqlrecap.model.DepenseWithType
import com.example.sqlrecap.model.Type
import com.example.sqlrecap.repos.ReposDateTimePicker
import com.example.sqlrecap.viewModel.DepenseViewModel
import com.example.sqlrecap.viewModel.DepensesArrray

class DepenseAddFragment : Fragment() {

    private lateinit var depenseViewModel: DepenseViewModel
    lateinit var binding: FragmentDepenseAddBinding
    private val args: DepenseAddFragmentArgs by navArgs()
    private var depenseSelected : DepenseWithType? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDepenseAddBinding.inflate(layoutInflater)
        depenseViewModel = ViewModelProvider(requireActivity()).get(DepenseViewModel::class.java)

        if(DepensesArrray.depenses == null) {DepensesArrray.depenses = listOf<DepenseWithType>() }

        args.depenseId?.let {it ->
            if(it == (0).toLong()){
                binding.btnDel.isEnabled = false
                binding.btOk.isEnabled = true
                depenseSelected = DepenseWithType(Depense(0,"",0,""), Type(1,"Autre"))
            } else {
                binding.btnDel.isEnabled = true
                binding.btOk.isEnabled = false
                depenseSelected = DepensesArrray.depenses!!.find { it ->
                    it.depense.depenseId == args.depenseId
                }
                SetData()
            }
        }
        ReposDateTimePicker.setupDatePicker(binding.editDate, requireActivity())
        setupButtonListeners()
        return binding.root
    }

    private fun SetData(){
        depenseSelected?.let { it ->
            binding.editName.setText(it.depense.nom)
            binding.editDate.setText(it.depense.date)
            binding.editPrix.setText(it.depense.prix.toString())
        }
    }

    private fun GetData(){
        depenseSelected!!.depense.nom = binding.editName.text.toString()
        depenseSelected!!.depense.date = binding.editDate.text.toString()
        depenseSelected!!.depense.prix = binding.editPrix.text.toString().toLong()
    }

    private fun setupButtonListeners() {

        binding.btnDel.setOnClickListener {
            depenseSelected?.let { it -> depenseViewModel.delDepense(requireActivity(), it.depense) }
            val action = DepenseAddFragmentDirections.actionDepenseAddFragmentToDepensesListeFragment()
            findNavController().navigate(action)
        }

        binding.btnCancel.setOnClickListener {
            val action = DepenseAddFragmentDirections.actionDepenseAddFragmentToDepensesListeFragment()
            findNavController().navigate(action)
        }

        binding.btOk.setOnClickListener {
            GetData()
            depenseSelected?.let { it ->
                depenseViewModel.addDepense(requireActivity(), it)
            }
            val action = DepenseAddFragmentDirections.actionDepenseAddFragmentToDepensesListeFragment()
            findNavController().navigate(action)
        }

    }

}