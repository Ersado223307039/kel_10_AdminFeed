package com.example.kel_10_adminfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.kel_10_adminfeed.databinding.ActivitySignUpMainBinding

class signUpMainActivity : AppCompatActivity() {
    private val binding: ActivitySignUpMainBinding by lazy {
        ActivitySignUpMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.CreateBotton.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
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
}