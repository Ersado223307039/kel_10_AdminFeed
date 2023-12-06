package com.example.kel_10_adminfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.kel_10_adminfeed.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.LoginBotton.setOnClickListener {
            val intent=Intent(this,signUpMainActivity::class.java)
            startActivity(intent)
        }
        binding.dontHaveAccountBotton.setOnClickListener {
            val intent=Intent(this,signUpMainActivity::class.java)
            startActivity(intent)
        }


    }
}