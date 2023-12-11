package com.example.kel_10_adminfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.kel_10_adminfeed.databinding.ActivityLoginBinding
import com.example.kel_10_adminfeed.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private  var userName :String?=null
    private  lateinit var email:String
    private  lateinit var password:String
    private  lateinit var auth:FirebaseAuth
    private  lateinit var database:DatabaseReference

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=Firebase.auth
        database=Firebase.database.reference

        binding.LoginBotton.setOnClickListener {

            email=binding.email.text.toString().trim()
            password=binding.password.text.toString().trim()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please Fill All Detail",Toast.LENGTH_SHORT).show()
            }else{
                createUserAccount(email,password)
            }


        }
        binding.dontHaveAccountBotton.setOnClickListener {
            val intent=Intent(this,signUpMainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user=auth.currentUser
                Toast.makeText(this,"Login Sucsesful",Toast.LENGTH_SHORT).show()
                updateUi(user)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        val user=auth.currentUser
                        Toast.makeText(this,"Create account & login succes",Toast.LENGTH_SHORT).show()
                        saveUserData()
                        updateUi(user)
                    }else{
                        Toast.makeText(this,"Authentification failed",Toast.LENGTH_SHORT).show()
                        Log.d("Account","createUserAccount: Authentification failed",task.exception)
                    }
                }
            }
        }

    }

    private fun saveUserData() {
        email=binding.email.text.toString().trim()
        password=binding.password.text.toString().trim()

        val user = UserModel(userName,email,password)
        val userId=FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }

    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
    }
}