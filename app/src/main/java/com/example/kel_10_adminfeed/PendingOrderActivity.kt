package com.example.kel_10_adminfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_adminfeed.adapter.PendingOrderAdapter
import com.example.kel_10_adminfeed.databinding.ActivityPendingOrderBinding
import com.example.kel_10_adminfeed.model.OrderDetails
import com.google.firebase.database.*

class PendingOrderActivity : AppCompatActivity(),PendingOrderAdapter.OnItemClicked{
    private lateinit var binding: ActivityPendingOrderBinding
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFristFoodOrder: MutableList<String> = mutableListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialization of database
        database = FirebaseDatabase.getInstance()
        // initialization of databaseReference
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()

        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun getOrderDetails() {
        // retrieve order details from Firebase database
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOfOrderItem) {
            // add data to respective list for populatig the recyclerView
            orderItem.username?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach{
                listOfImageFristFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.penddingOrderRecycleView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(this,listOfName,listOfTotalPrice,listOfImageFristFoodOrder,this)
        binding.penddingOrderRecycleView.adapter=adapter
    }
    override fun onItemClickListener(position: Int){
        val intent=Intent(this,OrderDetailsActivity::class.java)
        val userOrderDetails=listOfOrderItem[position]
        intent.putExtra("userOrderDetails",userOrderDetails)
        startActivity(intent)

    }

    override fun onItemAcceptClickListener(position: Int) {
        val childItemPushKey=listOfOrderItem[position].itemPushKey
        val clickItemOrderReference=childItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("orderAceepted")?.setValue(true)
        updateOrderAcceptStatus(position)

    }

    override fun onItemDispatchClickListener(position: Int) {
        val dispatchItemPushKey=listOfOrderItem[position].itemPushKey
        val dispatchItemOrderFreference=database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderFreference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deletThisFromOrderDetails(dispatchItemPushKey)
            }

    }

    private fun deletThisFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemReference = database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "order is Dispatch", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "order is not DISPATCH" , Toast.LENGTH_SHORT).show()
            }
    }


    private fun updateOrderAcceptStatus(position: Int) {
        val userIdOfClickedItem=listOfOrderItem[position].userid
        val pushKeyOfClickedItem=listOfOrderItem[position].itemPushKey
        val buyHistoryReference=database.reference.child("user").child(userIdOfClickedItem!!).child("BuyHistory").child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("orderAceepted").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAceepted").setValue(true)


    }

}