package com.ancient.ancient_handcraft.app.PojoObj.SignUp

import com.ancient.ancient_handcraft.app.PojoObj.Login.LoginPayloadpojo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserPayloadPojo(
    @SerializedName("firstName") var firstName: String = "",
    @SerializedName("lastName") var lastName: String = "",
    @SerializedName("mobileNo") var mobileNo: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("username") var username: String = "",
    @SerializedName("otp") var otp: Int = 0,
    @SerializedName("isAdmin") var isAdmin: Boolean = false,
    @SerializedName("password") var password: String = "",
    @SerializedName("id") var id: Int = 0,
    @SerializedName("type") var type: String = "",
    @SerializedName("token") var token: String = "",
    @SerializedName("refreshToken") var refreshToken: String = ""
) : Serializable

data class UserSession(
    @SerializedName("isLoggedIn") var isLoggedIn: Boolean = false,
    @SerializedName("user") var user: UserPayloadPojo? = null
) : Serializable