package com.example.sqlrecap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/*
class SpinnerType( SpinnetDataSet : List<String>) : BaseAdapter() {
    var _SpinnetDataSet: List<String> = SpinnetDataSet;

    override fun getCount(): Int {
        return _SpinnetDataSet.size
    }

    override fun getItem(position: Int): String {
        return _SpinnetDataSet[position]
    }

    override fun getItemId(position: Int): Long {
        return _SpinnetDataSet[position].toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding: SelectionRowBinding = SelectionRowBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        val viewHolder = SpinnetViewHolder(binding.root)

        val currentItem = getItem(position)
        viewHolder.SetupData(currentItem)

        return viewHolder.view


    }

}

 */