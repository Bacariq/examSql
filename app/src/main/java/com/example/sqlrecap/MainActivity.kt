package com.example.sqlrecap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sqlrecap.adapter.DepenseAdapter
import com.example.sqlrecap.databinding.ActivityMainBinding
import com.example.sqlrecap.model.DepenseWithType

class MainActivity : AppCompatActivity(), DepenseAdapter.OnLikeClickListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onLikeClick(depenseWithType: DepenseWithType) {
        Log.d("LM","onLikeClick" + depenseWithType.depense.nom)

    }

}