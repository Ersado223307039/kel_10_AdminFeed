package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_adminfeed.adapter.OrderDetailsAdapter
import com.example.kel_10_adminfeed.databinding.ActivityOrderDetailsBinding
import com.example.kel_10_adminfeed.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding:ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName:String?=null
    private var address:String?=null
    private var phoneNumber:String?=null
    private var totalPrice:String?=null
    private var foodNames:ArrayList<String> = arrayListOf()
    private var foodImages:ArrayList<String> = arrayListOf()
    private var foodQuantity:ArrayList<Int> = arrayListOf()
    private var foodPrices:ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backbutton.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

    private fun getDataFromIntent() {

        val recevedOrderDetails=intent.getSerializableExtra("userOrderDetails") as OrderDetails
        recevedOrderDetails?.let { orderDetails ->

                userName=recevedOrderDetails.username
                foodNames=recevedOrderDetails.foodNames as ArrayList<String>
                foodImages=recevedOrderDetails.foodImages  as ArrayList<String>
                foodQuantity=recevedOrderDetails.foodQuantities  as ArrayList<Int>
                address=recevedOrderDetails.address
                phoneNumber=recevedOrderDetails.phoneNumber
                foodPrices=recevedOrderDetails.foodPrices  as ArrayList<String>
                totalPrice=recevedOrderDetails.totalPrice

                setUserData()
                setAdapter()


        }

    }



    private fun setUserData() {
        binding.name.text=userName
        binding.address.text=address
        binding.phone.text=phoneNumber
        binding.totalPay.text=totalPrice

    }
    private fun setAdapter() {
        binding.orderDetailRecycleView.layoutManager= LinearLayoutManager(this)
        val adapter= OrderDetailsAdapter(this,foodNames,foodImages,foodQuantity,foodPrices)
        binding.orderDetailRecycleView.adapter=adapter


    }
}