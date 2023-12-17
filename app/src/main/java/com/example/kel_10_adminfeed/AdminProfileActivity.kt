package com.example.kel_10_adminfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kel_10_adminfeed.databinding.ActivityAdminProfileBinding
import com.example.kel_10_adminfeed.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminProfileActivity : AppCompatActivity() {
    private  val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        adminReference=database.reference.child("user")


        binding.backButton.setOnClickListener {
            finish()
        }
        binding.saveInfoButton.setOnClickListener {
            uploudUserData()
        }
        binding.name.isEnabled=false
        binding.address.isEnabled=false
        binding.email.isEnabled=false
        binding.phone.isEnabled=false
        binding.password.isEnabled=false
        binding.saveInfoButton.isEnabled=false

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
            binding.saveInfoButton.isEnabled=isEnabel


        }
        retriveUserData()


    }


    private fun retriveUserData() {
        val currentUid=auth.currentUser?.uid
        if (currentUid != null){
            val userReference=adminReference.child(currentUid)
            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var ownerName = snapshot.child("name").getValue()
                        var email = snapshot.child("email").getValue()
                        var password= snapshot.child("password").getValue()
                        var address = snapshot.child("address").getValue()
                        var phone = snapshot.child("phone").getValue()
                        setDataToTextView(ownerName,email,password,address,phone)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {
        binding.name.setText(ownerName.toString())
        binding.email.setText(email.toString())
        binding.password.setText(password.toString())
        binding.phone.setText(phone.toString())
        binding.address.setText(address.toString())



    }

    private fun uploudUserData() {
        val updateName =binding.name.text.toString()
        val updateEmail= binding.email.text.toString()
        val updatePassword=binding.password.text.toString()
        val updatePhone= binding.phone.text.toString()
        val updateAddress= binding.address.text.toString()
        val userData=UserModel(updateName,updateEmail,updatePassword,updatePhone,updateAddress)

        val currentUserId = auth.currentUser?.uid
        if(currentUserId !=null){
            val userReference=adminReference.child(currentUserId)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("address").setValue(updateAddress)
            Toast.makeText(this, "Profil Update Success full", Toast.LENGTH_SHORT).show()
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)



        } else {
            Toast.makeText(this, "Profil Update failed", Toast.LENGTH_SHORT).show()

        }

    }

}