package com.example.kel_10_adminfeed

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kel_10_adminfeed.databinding.ActivityAddItemBinding
import com.example.kel_10_adminfeed.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class AddItemActivity : AppCompatActivity() {
    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private lateinit var foodDescription:String
    private lateinit var foodIngredient:String
    private var foodImageUri:Uri?=null

    private lateinit var  auth :FirebaseAuth
    private lateinit var database: FirebaseDatabase


    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()

        binding.addItemBotton.setOnClickListener {
            foodName= binding.foodName.text.toString().trim()
            foodPrice= binding.foodPrice.text.toString().trim()
            foodDescription= binding.description.text.toString().trim()
            foodIngredient= binding.ingredient.text.toString().trim()

            if (!(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this,"item add sucessfull",Toast.LENGTH_SHORT).show()
                finish()

            }else{
                Toast.makeText(this,"Fill All the sucessfull",Toast.LENGTH_SHORT).show()

            }


        }
        binding.selectImage.setOnClickListener{
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            finish()
        }

     }

    private fun uploadData() {
        val menuRef = database.getReference("menu")
        val newItemKey= menuRef.push().key

        if (foodImageUri != null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->
                    //Create a new menu item
                    val newItem= AllMenu(
                        foodName= foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUrl.toString()


                    )
                    newItemKey?.let{
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"data uploud succes",Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener{
                                Toast.makeText(this,"data uploud failed",Toast.LENGTH_SHORT).show()
                            }
                    }
                }


            } .addOnFailureListener {
                Toast.makeText(this," image uploud failed",Toast.LENGTH_SHORT).show()
            }


        }else {
            Toast.makeText(this,"please select image",Toast.LENGTH_SHORT).show()
        }

    }

   private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if (uri !=null){
            binding.selectImage.setImageURI(uri)
            foodImageUri=uri
        }



    }

}
