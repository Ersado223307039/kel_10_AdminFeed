package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_adminfeed.adapter.AddItemAdapter
import com.example.kel_10_adminfeed.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val menuFoodName = listOf("burger","sesns","chickn","item","chickn","item")
        val menuItemPrice = listOf("$4","$3","8","$4","$3","8")
        val menuIMage= listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu5

        )
        val adapter= AddItemAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice),
            ArrayList(menuIMage)
        )
        binding.MenuRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.MenuRecyclerview.adapter=adapter

    }
}