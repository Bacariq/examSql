package com.example.sqlrecap.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sqlrecap.db.DepenseRepository
import com.example.sqlrecap.model.Depense
import com.example.sqlrecap.model.DepenseWithType
import com.example.sqlrecap.model.Type
import java.time.LocalDate


class DepenseViewModel: ViewModel() {
    //var liveDataBookList: LiveData<List<Book>>? = null

    fun getDepenses(context: Context): LiveData<List<DepenseWithType>> {
        return DepenseRepository.getAllDepenses(context)
    }

    fun addDepense(context: Context, depense : DepenseWithType) {
        val selectedType: Type = depense.types
        DepenseRepository.insertDepense(context,depense.depense.nom,  depense.depense.prix, depense.depense.date, selectedType)
    }

    fun delDepense(context: Context, depense : Depense) {
        DepenseRepository.deleteDepense(context, depense.depenseId)
    }

}