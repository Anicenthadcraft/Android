package com.ancient.ancient_handcraft.app.PojoObj.SignUp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterPayloadPojo (
    @SerializedName("firstName") var firstName: String = "",
    @SerializedName("lastName") var lastName: String = "",
    @SerializedName("mobileNo") var mobileNo: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("lastName") var isAdmin: Boolean = false,
    @SerializedName("lastName") var password: String = ""
):Serializable