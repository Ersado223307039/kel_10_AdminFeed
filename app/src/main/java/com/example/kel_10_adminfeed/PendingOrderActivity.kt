package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_adminfeed.adapter.DeliveryAdapter
import com.example.kel_10_adminfeed.adapter.PendingOrderAdapter
import com.example.kel_10_adminfeed.databinding.ActivityPendingOrderBinding
import com.example.kel_10_adminfeed.databinding.PendingOrderItemBinding

class PendingOrderActivity : AppCompatActivity() {
   private lateinit var binding: ActivityPendingOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        val orderedCustomerName = arrayListOf(
            "Diaa",
            "Aina",
            "Mark",
        )
        val orderedQuantity = arrayListOf(
            "9",
            "8",
            "7",
        )
        val orderedFoodImage = arrayListOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3)
        val adapter= PendingOrderAdapter(orderedCustomerName,orderedQuantity,orderedFoodImage, this)
        binding.penddingOrderRecycleView.adapter = adapter
        binding.penddingOrderRecycleView.layoutManager = LinearLayoutManager(this)

    }
}