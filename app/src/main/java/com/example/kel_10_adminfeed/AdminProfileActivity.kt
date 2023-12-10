package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kel_10_adminfeed.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private  val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.name.isEnabled=false
        binding.address.isEnabled=false
        binding.email.isEnabled=false
        binding.phone.isEnabled=false
        binding.password.isEnabled=false

        var isEnabel= false
        binding.editButton.setOnClickListener {
            isEnabel=! isEnabel
            binding.name.isEnabled=isEnabel
            binding.address.isEnabled=isEnabel
            binding.email.isEnabled=isEnabel
            binding.phone.isEnabled=isEnabel
            binding.password.isEnabled=isEnabel
            if(isEnabel){
                binding.name.requestFocus()
            }


        }


    }
}