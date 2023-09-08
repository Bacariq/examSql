package com.example.sqlrecap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlrecap.MainActivity
import com.example.sqlrecap.R
import com.example.sqlrecap.model.Depense
import com.example.sqlrecap.model.DepenseWithType


class DepenseAdapter(
    private val depenses: List<DepenseWithType>,
    private val onLikeClickListener: OnLikeClickListener
) : RecyclerView.Adapter<DepenseViewHolder>() {

    interface OnLikeClickListener {
        fun onLikeClick(depense: DepenseWithType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepenseViewHolder {
        val layout =  LayoutInflater.from(parent.context).inflate(R.layout.depense_item_list, parent, false)
        return DepenseViewHolder(layout, onLikeClickListener);
    }

    override fun onBindViewHolder(holder: DepenseViewHolder, position: Int) {
        val item : DepenseWithType = depenses[position];
        holder.SetupData(item)
    }

    override fun getItemCount(): Int {
        return depenses.size
    }
}


class DepenseViewHolder (
    val view : View,
    private val onLikeClickListener: DepenseAdapter.OnLikeClickListener
) : RecyclerView.ViewHolder(view) {

    val Titre : TextView = view.findViewById(R.id.depenseName)
    val Date : TextView = view.findViewById(R.id.depenseDate)
    val Prix : TextView = view.findViewById(R.id.depensePrix)
    val Type : TextView = view.findViewById(R.id.depenseType)

    public fun SetupData(depense : DepenseWithType){
        Titre.text = depense.depense.nom
        Date.text = depense.depense.date.toString()
        Type.text = depense.types.name.toString()
        Prix.text = depense.depense.prix.toString() + " €"

        view.setOnClickListener {
            onLikeClickListener.onLikeClick( depense)
        }
    }
}
