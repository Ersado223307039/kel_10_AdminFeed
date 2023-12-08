package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import com.bumptech.glide.Glide
import com.example.kel_10_adminfeed.databinding.ActivityAddItemBinding
import com.example.kel_10_adminfeed.databinding.ActivityAllItemBinding


class AddItemActivity : AppCompatActivity() {

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.selectImage.setOnClickListener{
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
        binding.backButton.setOnClickListener {
            finish()
        }

     }
    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
        if (uri !=null){
            binding.selectImage.setImageURI(uri)
        }



    }

}
