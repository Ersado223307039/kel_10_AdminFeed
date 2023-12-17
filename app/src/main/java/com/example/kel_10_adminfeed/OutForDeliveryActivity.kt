package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_adminfeed.adapter.DeliveryAdapter
import com.example.kel_10_adminfeed.databinding.ActivityOutForDeliveryBinding
import com.example.kel_10_adminfeed.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding:ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var listOfComplateOrderList:ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        retriveComplateOrderDetail()



    }

    private fun retriveComplateOrderDetail() {
        database= FirebaseDatabase.getInstance()
        val completeOrderReference = database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completeOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfComplateOrderList.clear()
                for (orderSnapshot in snapshot.children){
                    val complateOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    complateOrder?.let {
                        listOfComplateOrderList.add(it)
                    }

                }
                listOfComplateOrderList.reverse()
                setDataIntoRecycleView()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun setDataIntoRecycleView() {
        val customerName= mutableListOf<String>()
        val moneyStatus= mutableListOf<Boolean>()

        for (order in listOfComplateOrderList){
            order.username?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.paymentReceived)
        }
        val adapter= DeliveryAdapter(customerName,moneyStatus)
        binding.deliveryRecyclerView.adapter = adapter
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)


    }
}