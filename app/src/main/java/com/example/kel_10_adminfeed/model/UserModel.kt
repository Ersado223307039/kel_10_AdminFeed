package com.example.kel_10_adminfeed.model



data class UserModel(
    val name:String?=null,
    val email:String?=null,
    val password:String?=null,
    var address:String?=null,
    var phone:String?=null
)
