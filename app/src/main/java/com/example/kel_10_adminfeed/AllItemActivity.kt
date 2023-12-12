package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kel_10_adminfeed.adapter.MenuItemAdapter
import com.example.kel_10_adminfeed.databinding.ActivityAllItemBinding
import com.example.kel_10_adminfeed.model.AllMenu
import com.google.firebase.database.*

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private var menuItems:ArrayList<AllMenu> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference =FirebaseDatabase.getInstance().reference
        retriveMenuItem()


        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun retriveMenuItem() {
        database= FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")

        //fetch database

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data
               menuItems.clear()
                //loop each food item
               for (foodSnapShot in snapshot.children){
                   val menuItem = foodSnapShot.getValue(AllMenu::class.java)
                   menuItem?.let {
                       menuItems.add(it)
                   }
               }
                setAdapter()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error: ${error.message}")

            }


        })

    }
    private fun setAdapter() {
        val adapter= MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference)

        binding.MenuRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.MenuRecyclerview.adapter=adapter

    }
}