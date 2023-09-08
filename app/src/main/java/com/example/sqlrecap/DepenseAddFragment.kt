package com.example.sqlrecap

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
import com.google.android.material.internal.ViewUtils.hideKeyboard

class DepenseAddFragment : Fragment() {

    private lateinit var depenseViewModel: DepenseViewModel
    lateinit var binding: FragmentDepenseAddBinding
    private val args: DepenseAddFragmentArgs by navArgs()
    private var depenseSelected : DepenseWithType? = null
    private var typeSelected : Type? = null
    private var typesArray : List<Type>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDepenseAddBinding.inflate(layoutInflater)
        depenseViewModel = ViewModelProvider(requireActivity()).get(DepenseViewModel::class.java)

        if(DepensesArrray.depenses == null) {DepensesArrray.depenses = listOf<DepenseWithType>() }

        args.depenseId?.let {it ->
            if(it == (0).toLong()){
                binding.btnDel.isVisible = false
                binding.btOk.isVisible = true
                depenseSelected = DepenseWithType(Depense(0,"",0,""), Type(1,"Autre"))
            } else {
                binding.btOk.isVisible = false
                binding.btnDel.isVisible = true
                depenseSelected = DepensesArrray.depenses!!.find { it ->
                    it.depense.depenseId == args.depenseId
                }
                SetData()
            }
        }

        depenseViewModel.getType(requireActivity()).observeForever {
            typesArray = it
        }

        ReposDateTimePicker.setupDatePicker(binding.editDate, requireActivity())
        setupButtonListeners()
        return binding.root
    }

    private fun ShowType(){
        depenseSelected?.let { pffff ->

            val items: List<String> = typesArray?.map { it.name } ?: emptyList()

            val alertDialogBuilder = AlertDialog.Builder( requireActivity())
            alertDialogBuilder.setTitle("Sélectionnez un élément")

            val itemClickListener = DialogInterface.OnClickListener { _, which ->
                val typeSelected: Type? = typesArray?.find { it -> it.name == items[which] }
                if (typeSelected != null) {
                    pffff.types = typeSelected
                }
                binding.editType.setText(items[which])
            }

            alertDialogBuilder.setItems(items.toTypedArray(), itemClickListener)

            alertDialogBuilder.setNegativeButton("Annuler") { dialog, _ ->
                dialog.dismiss()
                pffff.types = Type(1, "Autre")
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun SetData(){
        depenseSelected?.let { it ->
            binding.editName.setText(it.depense.nom)
            binding.editDate.setText(it.depense.date)
            binding.editPrix.setText(it.depense.prix.toString())
            binding.editType.setText(it.types.name)
        }
    }

    private fun GetData(){
        depenseSelected!!.depense.nom = binding.editName.text.toString()
        depenseSelected!!.depense.date = binding.editDate.text.toString()
        depenseSelected!!.depense.prix = binding.editPrix.text.toString().toLong()
    }

    fun CheckData() : Boolean{
        var bRet : Boolean = true
        depenseSelected?.let { it ->
            bRet = true
            if (it.depense.prix == null || it.depense.prix <= 0){
                bRet = false
            }
            if (it.depense.nom == null || it.depense.nom == ""){
                bRet = false
            }
            if (it.depense.date == null || it.depense.date == ""){
                bRet = false
            }
            if (it.types.name == null || it.depense.date == ""){
                bRet = false
            }
        }
        return bRet;
    }

    private fun setupButtonListeners() {

        binding.btnAddType.setOnClickListener {
            ShowType()
        }


        binding.btnDel.setOnClickListener {
            depenseSelected?.let { it ->
                depenseViewModel.delDepense(
                    requireActivity(),
                    it.depense
                )
            }
            val action =
                DepenseAddFragmentDirections.actionDepenseAddFragmentToDepensesListeFragment()
            findNavController().navigate(action)
        }

        binding.btnCancel.setOnClickListener {
            val action =
                DepenseAddFragmentDirections.actionDepenseAddFragmentToDepensesListeFragment()
            findNavController().navigate(action)
        }

        binding.btOk.setOnClickListener {
            GetData()
            if (CheckData() == true) {
                depenseSelected?.let { it ->
                    depenseViewModel.addDepense(requireActivity(), it)
                }
                val action =
                    DepenseAddFragmentDirections.actionDepenseAddFragmentToDepensesListeFragment()
                findNavController().navigate(action)
            } else {
                showErrorDialog("Erreur de données", "Veuillez vérifier vos données.")
            }
        }

    }
        private fun showErrorDialog(title: String, message: String) {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder.setMessage(message)

            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
}