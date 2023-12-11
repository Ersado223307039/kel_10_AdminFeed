package com.example.kel_10_adminfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kel_10_adminfeed.databinding.ActivitySignUpMainBinding
import com.example.kel_10_adminfeed.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class signUpMainActivity : AppCompatActivity() {
    private lateinit var userName :String
    private lateinit var email: String
    private lateinit var password:String
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference

    private val binding: ActivitySignUpMainBinding by lazy {
        ActivitySignUpMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initiaalize firebase Auth
        auth = Firebase.auth
        //initialize Firebase database
        database=Firebase.database.reference


        binding.createUserBotton.setOnClickListener {
            //get text from editext
            userName = binding.name.text.toString().trim()
            email = binding.emailorPhone.text.toString().trim()
            password = binding.passsword.text.toString().trim()

            if (userName.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this,"please fill all the detail",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)

            }

        }
        binding.AllreadyHaveAccount.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        val locationList= arrayListOf("madiun","magetan","maospati")
        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView= binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Succesfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Account Creation failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }
        }
    }

    private fun saveUserData() {
        userName = binding.name.text.toString().trim()
        email = binding.emailorPhone.text.toString().trim()
        password = binding.passsword.text.toString().trim()

        val user= UserModel(userName,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)

    }
}