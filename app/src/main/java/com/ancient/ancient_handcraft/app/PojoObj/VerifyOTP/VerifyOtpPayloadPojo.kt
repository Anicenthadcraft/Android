package com.ancient.ancient_handcraft.app.PojoObj.VerifyOTP

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VerifyOtpPayloadPojo(@SerializedName("firstName") var firstName: String = "") :
    Serializable