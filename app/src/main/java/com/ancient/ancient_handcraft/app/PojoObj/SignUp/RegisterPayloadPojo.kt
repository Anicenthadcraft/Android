package com.ancient.ancient_handcraft.app.PojoObj.SignUp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterPayloadPojo(
    @SerializedName("firstName") var firstName: String = "",
    @SerializedName("lastName") var lastName: String = "",
    @SerializedName("mobileNo") var mobileNo: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("username") var username: String = "",
    @SerializedName("otp") var otp: Int = 0,
    @SerializedName("isAdmin") var isAdmin: Boolean = false,
    @SerializedName("password") var password: String = "",
    @SerializedName("id") var id: Int = 0
) : Serializable