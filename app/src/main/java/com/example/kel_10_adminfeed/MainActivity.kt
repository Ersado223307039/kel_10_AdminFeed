package com.example.kel_10_adminfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kel_10_adminfeed.databinding.ActivityMainBinding
import com.example.kel_10_adminfeed.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private  val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var completedOrderReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addMenu.setOnClickListener {
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItemMenu.setOnClickListener {
            val intent = Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.outForDeliveryButton.setOnClickListener {
            val intent = Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.profile.setOnClickListener {
            val intent = Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.createUser.setOnClickListener {
            val intent = Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.PendingOrdertextView.setOnClickListener {
            val intent = Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
        binding.logOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,signUpMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        pendingOrder()
        competedOrders()
        wholeTimeErning()

    }

    private fun wholeTimeErning() {
        var listOfTotalPay = mutableListOf<Int>()
        completedOrderReference=FirebaseDatabase.getInstance().reference.child("CompletedOrder")

        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children){
                    var complateOrder=orderSnapshot.getValue(OrderDetails::class.java)

                    complateOrder?.totalPrice?.replace("$","")?.toIntOrNull()
                        ?.let {i ->
                            listOfTotalPay.add(i)
                        }

                }
                binding.wholeTimeLearning.text= listOfTotalPay.sum().toString() + ",00"


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun competedOrders() {
        val complateOrderReference = database.reference.child("CompletedOrder")
        var complateOrderItemCount = 0
        complateOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                complateOrderItemCount = snapshot.childrenCount.toInt()
                binding.complateOrder.text= complateOrderItemCount.toString()


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    private fun pendingOrder() {
        database= FirebaseDatabase.getInstance()
        val pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrders.text= pendingOrderItemCount.toString()


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

}